package com.dzenlab.adviceandinsult.usecase.advice

import com.dzenlab.adviceandinsult.models.AdviceCreateDB
import com.dzenlab.adviceandinsult.models.AdviceIdDB
import com.dzenlab.adviceandinsult.repository.AdviceRepository

//import java.util.logging.Level
//import java.util.logging.Logger
//Logger.getLogger("MY_LOG").log(Level.INFO, "")

class InsertAdviceToDatabaseUseCase(private val adviceRepository: AdviceRepository) {

    suspend fun execute(adviceCreateDB: AdviceCreateDB): AdviceIdDB =
        adviceRepository.insertAdviceToDatabase(adviceCreateDB)
}