package com.dzenlab.adviceandinsult.usecase.advice

import com.dzenlab.adviceandinsult.models.AdviceDeleteDB
import com.dzenlab.adviceandinsult.repository.AdviceRepository

//import java.util.logging.Level
//import java.util.logging.Logger
//Logger.getLogger("MY_LOG").log(Level.INFO, "")

class DeleteAdviceByIdFromDatabaseUseCase(private val adviceRepository: AdviceRepository) {

    suspend fun execute(adviceDeleteDB: AdviceDeleteDB) =
        adviceRepository.deleteAdviceByIdFromDatabase(adviceDeleteDB)
}