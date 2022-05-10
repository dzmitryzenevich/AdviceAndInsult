package com.dzenlab.adviceandinsult.presentation.fragment.advice

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.*
import com.dzenlab.adviceandinsult.models.*
import com.dzenlab.adviceandinsult.usecase.advice.*
import com.dzenlab.adviceandinsult.presentation.mapper.toAdvice
import com.dzenlab.adviceandinsult.presentation.mapper.toAdviceCreateDB
import com.dzenlab.adviceandinsult.presentation.mapper.toAdviceNumberDB
import com.dzenlab.adviceandinsult.presentation.mapper.toAdviceSP
import com.dzenlab.adviceandinsult.presentation.models.Advice
import com.dzenlab.adviceandinsult.presentation.models.CreateItemError
import com.dzenlab.adviceandinsult.presentation.models.ResponseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class AdviceViewModel @Inject constructor(
    private val getAdviceFromNetworkUseCase: GetAdviceFromNetworkUseCase,
    private val getAdviceFromSharePrefUseCase: GetAdviceFromSharePrefUseCase,
    private val saveAdviceToSharePrefUseCase: SaveAdviceToSharePrefUseCase,
    private val isExistAdviceToDatabaseUseCase: IsExistAdviceToDatabaseUseCase,
    private val insertAdviceToDatabaseUseCase: InsertAdviceToDatabaseUseCase
) : ViewModel() {

    private val _getAdviceResponse = MutableLiveData<ResponseVM>()
    val getAdviceResponse: LiveData<ResponseVM> = _getAdviceResponse

    private val _processing = MutableLiveData(false)
    val processing: LiveData<Boolean> = _processing

    private val _buttonGet = MutableLiveData(true)
    val buttonGet: LiveData<Boolean> = _buttonGet

    private val _progress = MutableLiveData(0)
    val progress: LiveData<Int> = _progress

    val saveAdviceResponse = MutableLiveData<ResponseVM?>(null)

    private val advice = MutableLiveData(Advice(number = 0, advice = "no data"))

    private var job: Job? = null

    private val handler: Handler = Handler(Looper.getMainLooper())


    init {

        getAdviceFromSharePref()
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

                job = getAdviceFromNetwork()
            }
        }
    }

    fun clickSaveButton() = viewModelScope.launch {

        try {

            advice.value?.let { advice ->

                if(advice.number == 0) {

                    saveAdviceResponse.value = ResponseVM.Error(code = CreateItemError.NEW_ITEM)

                } else {

                    if(isExistAdviceToDatabaseUseCase.execute(advice.toAdviceNumberDB()).isExist) {

                        saveAdviceResponse.value =
                            ResponseVM.Error(code = CreateItemError.ITEM_IS_EXIST)

                    } else {

                        val id = insertAdviceToDatabaseUseCase.execute(advice.toAdviceCreateDB()).id

                        saveAdviceResponse.value = ResponseVM.Success(data = id)
                    }
                }
            }

        } catch (e: Exception) {

            saveAdviceResponse.value = ResponseVM.Exceptions(message = e.message)
        }
    }

    private fun getAdviceFromSharePref() = viewModelScope.launch {

        try {

            val adviceValue = Advice.toAdvice(getAdviceFromSharePrefUseCase.execute())

            advice.value = adviceValue

            _getAdviceResponse.value = ResponseVM.Success(data = adviceValue.advice)

        } catch (e: Exception) {

            _getAdviceResponse.value = ResponseVM.Exceptions(message = e.message)
        }
    }

    private fun getAdviceFromNetwork(): Job = viewModelScope.launch {

        try {

            getAdviceFromNetworkUseCase.execute().flowOn(Dispatchers.IO).collect { response ->

                when(response) {

                    is ResponseNet.Success<*> -> {

                        val adviceValue = Advice.toAdvice(response.data as AdviceNet)

                        saveAdviceToSharePrefUseCase.execute(adviceValue.toAdviceSP())

                        advice.value = adviceValue

                        _getAdviceResponse.value = ResponseVM.Success(data = adviceValue.advice)

                        stop()
                    }

                    is ResponseNet.Error -> {

                        _getAdviceResponse.value = ResponseVM.Exceptions(message = response.error)

                        stop()
                    }

                    is ResponseNet.Progress -> _progress.value = response.progress
                }
            }

        } catch (ce: CancellationException) {

            _buttonGet.value?.let { if(!it) { setData(true) } } ?: setData(true)

            stop()

        } catch (e: Exception) {

            _getAdviceResponse.value = ResponseVM.Exceptions(message = e.message)

            stop()
        }
    }

    private fun setData(flag: Boolean) {

        if(flag) {

            _buttonGet.value = true

            _getAdviceResponse.value = ResponseVM.Success(data = advice.value?.advice ?: "")

        } else {

            _buttonGet.value = false

            _getAdviceResponse.value = ResponseVM.Success(data = "")
        }
    }

    private fun stop() {

        handler.removeCallbacksAndMessages(null)

        _processing.value = false

        _buttonGet.value = true
    }
}