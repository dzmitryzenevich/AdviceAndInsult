package com.dzenlab.adviceandinsult.usecase.advice

import com.dzenlab.adviceandinsult.models.AdviceCountDB
import com.dzenlab.adviceandinsult.repository.AdviceRepository
import kotlinx.coroutines.flow.Flow

//import java.util.logging.Level
//import java.util.logging.Logger
//Logger.getLogger("MY_LOG").log(Level.INFO, "")

class GetAdviceCountFromDatabaseUseCase(private val adviceRepository: AdviceRepository) {

    fun execute(): Flow<AdviceCountDB> = adviceRepository.getAdviceCountFromDatabase()
}