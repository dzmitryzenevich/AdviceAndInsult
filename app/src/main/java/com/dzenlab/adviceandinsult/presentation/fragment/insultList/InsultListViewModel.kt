package com.dzenlab.adviceandinsult.presentation.fragment.insultList

import androidx.lifecycle.*
import com.dzenlab.adviceandinsult.models.InsultDeleteDB
import com.dzenlab.adviceandinsult.models.InsultGetDB
import com.dzenlab.adviceandinsult.models.InsultUpdateDB
import com.dzenlab.adviceandinsult.usecase.insult.DeleteInsultByIdFromDatabaseUseCase
import com.dzenlab.adviceandinsult.usecase.insult.GetAllInsultFromDatabaseUseCase
import com.dzenlab.adviceandinsult.usecase.insult.UpdateInsultToDatabaseUseCase
import com.dzenlab.adviceandinsult.presentation.models.ResponseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InsultListViewModel @Inject constructor(
    getAllInsultFromDatabaseUseCase: GetAllInsultFromDatabaseUseCase,
    private val deleteInsultByIdFromDatabaseUseCase: DeleteInsultByIdFromDatabaseUseCase,
    private val updateInsultToDatabaseUseCase: UpdateInsultToDatabaseUseCase
) : ViewModel() {

    private val _emptyList = MutableLiveData<Boolean>()
    val emptyList: LiveData<Boolean> = _emptyList

    val insultList: LiveData<ResponseVM> = getAllInsultFromDatabaseUseCase.execute()
        .map { check(it) }
        .flowOn(Dispatchers.Default)
        .catch { e -> emit(ResponseVM.Exceptions(e.message)) }
        .shareIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            replay = 1).asLiveData()

    val deleteInsultResponse = MutableLiveData<ResponseVM?>(null)

    fun deleteInsult(id: Long) = viewModelScope.launch {

        try {

            deleteInsultByIdFromDatabaseUseCase.execute(InsultDeleteDB(id))

            deleteInsultResponse.value = ResponseVM.Success(Unit)

        } catch (e: Exception) {

            deleteInsultResponse.value = ResponseVM.Exceptions(e.message)
        }
    }

    fun updateInsult(id: Long, insult: String) = viewModelScope.launch {

        try {

            updateInsultToDatabaseUseCase.execute(InsultUpdateDB(id = id, insult = insult))

        } catch (ignore: Exception) {}
    }

    fun emptyList(isEmpty: Boolean) = viewModelScope.launch {

        withContext(Dispatchers.IO) { delay(100) }

        _emptyList.value = isEmpty
    }

    private fun check(list: List<InsultGetDB>): ResponseVM = ResponseVM.Success(list)
}