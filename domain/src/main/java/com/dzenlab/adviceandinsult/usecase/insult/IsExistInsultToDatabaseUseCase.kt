package com.dzenlab.adviceandinsult.usecase.insult

import com.dzenlab.adviceandinsult.models.InsultIsExistDB
import com.dzenlab.adviceandinsult.models.InsultNumberDB
import com.dzenlab.adviceandinsult.repository.InsultRepository

//import java.util.logging.Level
//import java.util.logging.Logger
//Logger.getLogger("MY_LOG").log(Level.INFO, "")

class IsExistInsultToDatabaseUseCase(private val insultRepository: InsultRepository) {

    suspend fun execute(insultNumberDB: InsultNumberDB): InsultIsExistDB =
        insultRepository.isExistInsultByNumberToDatabase(insultNumberDB)
}