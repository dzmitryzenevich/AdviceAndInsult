package com.dzenlab.adviceandinsult.usecase.insult

import com.dzenlab.adviceandinsult.models.InsultCreateDB
import com.dzenlab.adviceandinsult.models.InsultIdDB
import com.dzenlab.adviceandinsult.repository.InsultRepository

//import java.util.logging.Level
//import java.util.logging.Logger
//Logger.getLogger("MY_LOG").log(Level.INFO, "")

class InsertInsultToDatabaseUseCase(private val insultRepository: InsultRepository) {

    suspend fun execute(insultCreateDB: InsultCreateDB): InsultIdDB =
        insultRepository.insertInsultToDatabase(insultCreateDB)
}