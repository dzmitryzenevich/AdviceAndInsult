package com.dzenlab.adviceandinsult.presentation.fragment.insult

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzenlab.adviceandinsult.models.*
import com.dzenlab.adviceandinsult.usecase.insult.*
import com.dzenlab.adviceandinsult.presentation.mapper.*
import com.dzenlab.adviceandinsult.presentation.models.CreateItemError
import com.dzenlab.adviceandinsult.presentation.models.Insult
import com.dzenlab.adviceandinsult.presentation.models.InsultAndComment
import com.dzenlab.adviceandinsult.presentation.models.ResponseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsultViewModel @Inject constructor(
    private val getInsultFromNetworkUseCase: GetInsultFromNetworkUseCase,
    private val getInsultFromSharePrefUseCase: GetInsultFromSharePrefUseCase,
    private val saveInsultToSharePrefUseCase: SaveInsultToSharePrefUseCase,
    private val isExistInsultToDatabaseUseCase: IsExistInsultToDatabaseUseCase,
    private val insertInsultToDatabaseUseCase: InsertInsultToDatabaseUseCase
) : ViewModel() {

    private val _getInsultResponse = MutableLiveData<ResponseVM>()
    val getInsultResponse: LiveData<ResponseVM> = _getInsultResponse

    private val _processing = MutableLiveData(false)
    val processing: LiveData<Boolean> = _processing

    private val _buttonGet = MutableLiveData(true)
    val buttonGet: LiveData<Boolean> = _buttonGet

    private val _progress = MutableLiveData(0)
    val progress: LiveData<Int> = _progress

    val saveInsultResponse = MutableLiveData<ResponseVM?>(null)

    private val insult = MutableLiveData(Insult(
        number = 0,
        insult = "no data",
        comment = "no data")
    )

    private var job: Job? = null

    private val handler: Handler = Handler(Looper.getMainLooper())


    init {

        getInsultFromSharePref()
    }

    override fun onCleared() {

        super.onCleared()

        handler.removeCallbacksAndMessages(null)

        job = null
    }

    fun clickGetButton() {

        _processing.value?.let { processing ->

            if(processing) {

                handler.removeCallbacksAndMessages(null)

                _processing.value = false

                job?.cancel(CancellationException())

            } else {

                handler.postDelayed({ setData(false) }, 100)

                _processing.value = true

                job = getInsultFromNetwork()
            }
        }
    }

    fun clickSaveButton() = viewModelScope.launch {

        try {

            insult.value?.let { insult ->

                if(insult.number == 0) {

                    saveInsultResponse.value = ResponseVM.Error(code = CreateItemError.NEW_ITEM)

                } else {

                    if(isExistInsultToDatabaseUseCase
                            .execute(insult.toInsultNumberDB()).isExist) {

                        saveInsultResponse.value =
                            ResponseVM.Error(code = CreateItemError.ITEM_IS_EXIST)

                    } else {

                        val id = insertInsultToDatabaseUseCase.execute(insult.toInsultCreateDB()).id

                        saveInsultResponse.value = ResponseVM.Success(data = id)
                    }
                }
            }

        } catch (e: Exception) {

            saveInsultResponse.value = ResponseVM.Exceptions(message = e.message)
        }
    }

    private fun getInsultFromSharePref() = viewModelScope.launch {

        try {

            val insultValue = Insult.toInsult(getInsultFromSharePrefUseCase.execute())

            insult.value = insultValue

            _getInsultResponse.value = ResponseVM.Success(data = insultValue.toInsultAndComment())

        } catch (e: Exception) {

            _getInsultResponse.value = ResponseVM.Exceptions(message = e.message)
        }
    }

    private fun getInsultFromNetwork(): Job = viewModelScope.launch {

        try {

            getInsultFromNetworkUseCase.execute().flowOn(Dispatchers.IO).collect { response ->

                when(response) {

                    is ResponseNet.Success<*> -> {

                        val insultValue = Insult.toInsult(response.data as InsultNet)

                        saveInsultToSharePrefUseCase.execute(insultValue.toInsultSP())

                        insult.value = insultValue

                        _getInsultResponse.value = ResponseVM.Success(
                            data = insultValue.toInsultAndComment())

                        stop()
                    }

                    is ResponseNet.Error -> {

                        _getInsultResponse.value = ResponseVM.Exceptions(message = response.error)

                        stop()
                    }

                    is ResponseNet.Progress -> _progress.value = response.progress
                }
            }

        } catch (ce: CancellationException) {

            _buttonGet.value?.let { if(!it) { setData(true) } } ?: setData(true)

            stop()

        } catch (e: Exception) {

            _getInsultResponse.value = ResponseVM.Exceptions(message = e.message)

            stop()
        }
    }

    private fun setData(flag: Boolean) {

        if(flag) {

            _buttonGet.value = true

            val data = insult.value?.toInsultAndComment() ?:
            InsultAndComment(insult = "", comment = "")

            _getInsultResponse.value = ResponseVM.Success(data = data)

        } else {

            _buttonGet.value = false

            _getInsultResponse.value = ResponseVM.Success(data = InsultAndComment(
                insult = "", comment = ""))
        }
    }

    private fun stop() {

        handler.removeCallbacksAndMessages(null)

        _processing.value = false

        _buttonGet.value = true
    }
}