package com.dzenlab.adviceandinsult.repository

import com.dzenlab.adviceandinsult.models.*
import kotlinx.coroutines.flow.Flow

interface InsultRepository {

    suspend fun getInsultFromNetwork(): ResponseNet

    suspend fun getInsultFromSharePref(): InsultSP

    suspend fun saveInsultToSharePref(insultSP: InsultSP)

    suspend fun insertInsultToDatabase(insultCreateDB: InsultCreateDB): InsultIdDB

    fun getAllInsultFromDatabase(): Flow<List<InsultGetDB>>

    fun getInsultCountFromDatabase(): Flow<InsultCountDB>

    suspend fun isExistInsultByNumberToDatabase(insultNumberDB: InsultNumberDB): InsultIsExistDB

    suspend fun updateInsultToDatabase(insultUpdateDB: InsultUpdateDB)

    suspend fun deleteInsultByIdFromDatabase(insultDeleteDB: InsultDeleteDB)
}