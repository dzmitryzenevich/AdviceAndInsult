package com.dzenlab.adviceandinsult.usecase.advice

import com.dzenlab.adviceandinsult.models.AdviceIsExistDB
import com.dzenlab.adviceandinsult.models.AdviceNumberDB
import com.dzenlab.adviceandinsult.repository.AdviceRepository

//import java.util.logging.Level
//import java.util.logging.Logger
//Logger.getLogger("MY_LOG").log(Level.INFO, "")

class IsExistAdviceToDatabaseUseCase(private val adviceRepository: AdviceRepository) {

    suspend fun execute(adviceNumberDB: AdviceNumberDB): AdviceIsExistDB =
        adviceRepository.isExistAdviceByNumberToDatabase(adviceNumberDB)
}