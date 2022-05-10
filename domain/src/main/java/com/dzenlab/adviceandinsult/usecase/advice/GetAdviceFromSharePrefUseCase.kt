package com.dzenlab.adviceandinsult.usecase.advice

import com.dzenlab.adviceandinsult.models.AdviceSP
import com.dzenlab.adviceandinsult.repository.AdviceRepository

//import java.util.logging.Level
//import java.util.logging.Logger
//Logger.getLogger("MY_LOG").log(Level.INFO, "")

class GetAdviceFromSharePrefUseCase(private val adviceRepository: AdviceRepository) {

    suspend fun execute(): AdviceSP = adviceRepository.getAdviceFromSharePref()
}