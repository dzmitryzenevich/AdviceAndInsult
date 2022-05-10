package com.dzenlab.adviceandinsult.presentation.fragment.adviceList

import androidx.lifecycle.*
import com.dzenlab.adviceandinsult.models.AdviceDeleteDB
import com.dzenlab.adviceandinsult.models.AdviceGetDB
import com.dzenlab.adviceandinsult.usecase.advice.DeleteAdviceByIdFromDatabaseUseCase
import com.dzenlab.adviceandinsult.usecase.advice.DeleteAdvicesByIdsFromDatabaseUseCase
import com.dzenlab.adviceandinsult.usecase.advice.GetAllAdviceFromDatabaseUseCase
import com.dzenlab.adviceandinsult.presentation.models.ResponseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AdviceListViewModel @Inject constructor(
    getAllAdviceFromDatabaseUseCase: GetAllAdviceFromDatabaseUseCase,
    private val deleteAdviceByIdFromDatabaseUseCase: DeleteAdviceByIdFromDatabaseUseCase,
    private val deleteAdvicesByIdsFromDatabaseUseCase: DeleteAdvicesByIdsFromDatabaseUseCase
) : ViewModel() {

    private val _deleteList = MutableLiveData<List<Long>>(ArrayList())
    val deleteList: LiveData<List<Long>> = _deleteList

    private val _emptyList = MutableLiveData<Boolean>()
    val emptyList: LiveData<Boolean> = _emptyList

    val adviceList: LiveData<ResponseVM> = getAllAdviceFromDatabaseUseCase.execute()
        .map { check(it) }
        .flowOn(Dispatchers.Main)
        .catch { e -> emit(ResponseVM.Exceptions(e.message)) }
        .shareIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            replay = 1).asLiveData()

    val deleteAdviceResponse = MutableLiveData<ResponseVM?>(null)



    fun deleteAdvice(id: Long) = viewModelScope.launch {

        try {

            deleteAdviceByIdFromDatabaseUseCase.execute(AdviceDeleteDB(id))

            _deleteList.value?.let { deleteList ->

                if(deleteList.contains(id)) {

                    _deleteList.value = deleteList.minus(id)
                }
            }

            deleteAdviceResponse.value = ResponseVM.Success(Unit)

        } catch (e: Exception) {

            deleteAdviceResponse.value = ResponseVM.Exceptions(e.message)
        }
    }

    fun addDelDeleteItem(id: Long) {

        _deleteList.value?.let { list ->

            if(list.contains(id)) {

                _deleteList.value = list.minus(id)

            } else {

                _deleteList.value = list.plus(id)
            }
        }
    }

    fun deleteChecked() = viewModelScope.launch {

        try {

            _deleteList.value?.let { longList ->

                val list: List<AdviceDeleteDB> = longList.map { AdviceDeleteDB(it) }

                deleteAdvicesByIdsFromDatabaseUseCase.execute(list)
            }

        } catch (ignore: Exception) { } finally {

            _deleteList.value = ArrayList()
        }
    }

    fun emptyList(isEmpty: Boolean) = viewModelScope.launch {

        withContext(Dispatchers.IO) { delay(100) }

        _emptyList.value = isEmpty
    }

    private fun check(list: List<AdviceGetDB>): ResponseVM = ResponseVM.Success(list)
}