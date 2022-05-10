package com.dzenlab.adviceandinsult.usecase.insult

import com.dzenlab.adviceandinsult.models.InsultCountDB
import com.dzenlab.adviceandinsult.repository.InsultRepository
import kotlinx.coroutines.flow.Flow

//import java.util.logging.Level
//import java.util.logging.Logger
//Logger.getLogger("MY_LOG").log(Level.INFO, "")

class GetInsultCountFromDatabaseUseCase(private val insultRepository: InsultRepository) {

    fun execute(): Flow<InsultCountDB> = insultRepository.getInsultCountFromDatabase()
}