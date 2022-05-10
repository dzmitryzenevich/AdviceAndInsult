package com.dzenlab.adviceandinsult.usecase.insult

import com.dzenlab.adviceandinsult.models.InsultSP
import com.dzenlab.adviceandinsult.repository.InsultRepository

//import java.util.logging.Level
//import java.util.logging.Logger
//Logger.getLogger("MY_LOG").log(Level.INFO, "")

class SaveInsultToSharePrefUseCase(private val insultRepository: InsultRepository) {

    suspend fun execute(insultSP: InsultSP) = insultRepository.saveInsultToSharePref(insultSP)
}