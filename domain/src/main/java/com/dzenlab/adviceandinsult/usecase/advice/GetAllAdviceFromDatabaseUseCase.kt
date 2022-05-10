package com.dzenlab.adviceandinsult.usecase.advice

import com.dzenlab.adviceandinsult.models.AdviceGetDB
import com.dzenlab.adviceandinsult.repository.AdviceRepository
import kotlinx.coroutines.flow.Flow

//import java.util.logging.Level
//import java.util.logging.Logger
//Logger.getLogger("MY_LOG").log(Level.INFO, "")

class GetAllAdviceFromDatabaseUseCase(private val adviceRepository: AdviceRepository) {

    fun execute(): Flow<List<AdviceGetDB>> = adviceRepository.getAllAdviceFromDatabase()
}