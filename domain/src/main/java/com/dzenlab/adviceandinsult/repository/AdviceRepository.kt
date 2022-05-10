package com.dzenlab.adviceandinsult.repository

import com.dzenlab.adviceandinsult.models.*
import kotlinx.coroutines.flow.Flow

interface AdviceRepository {

    suspend fun getAdviceFromNetwork(): ResponseNet

    suspend fun getAdviceFromSharePref(): AdviceSP

    suspend fun saveAdviceToSharePref(adviceSP: AdviceSP)

    suspend fun insertAdviceToDatabase(adviceCreateDB: AdviceCreateDB): AdviceIdDB

    fun getAllAdviceFromDatabase(): Flow<List<AdviceGetDB>>

    fun getAdviceCountFromDatabase(): Flow<AdviceCountDB>

    suspend fun isExistAdviceByNumberToDatabase(adviceNumberDB: AdviceNumberDB): AdviceIsExistDB

    suspend fun updateAdviceToDatabase(adviceUpdateDB: AdviceUpdateDB)

    suspend fun deleteAdviceByIdFromDatabase(adviceDeleteDB: AdviceDeleteDB)

    suspend fun deleteAdvicesByIdsFromDatabase(list: List<AdviceDeleteDB>)
}