package com.dzenlab.adviceandinsult.presentation.fragment.home

import androidx.lifecycle.*
import com.dzenlab.adviceandinsult.models.AdviceCountDB
import com.dzenlab.adviceandinsult.models.InsultCountDB
import com.dzenlab.adviceandinsult.usecase.advice.GetAdviceCountFromDatabaseUseCase
import com.dzenlab.adviceandinsult.usecase.insult.GetInsultCountFromDatabaseUseCase
import com.dzenlab.adviceandinsult.presentation.models.ResponseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAdviceCountFromDatabaseUseCase: GetAdviceCountFromDatabaseUseCase,
    getInsultCountFromDatabaseUseCase: GetInsultCountFromDatabaseUseCase
) : ViewModel(){

    val countAdvice: LiveData<ResponseVM> = getAdviceCountFromDatabaseUseCase.execute()
        .map { map(it) }
        .flowOn(Dispatchers.Default)
        .catch { e -> emit(ResponseVM.Exceptions(e.message)) }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            replay = 1).asLiveData()

    val countInsult: LiveData<ResponseVM> = getInsultCountFromDatabaseUseCase.execute()
        .map { map(it) }
        .flowOn(Dispatchers.Default)
        .catch { e -> emit(ResponseVM.Exceptions(e.message)) }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            replay = 1).asLiveData()

    private fun map(adviceCountDB: AdviceCountDB): ResponseVM =
        ResponseVM.Success(adviceCountDB.count.toString())

    private fun map(insultCountDB: InsultCountDB): ResponseVM =
        ResponseVM.Success(insultCountDB.count.toString())
}