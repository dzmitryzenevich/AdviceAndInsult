package com.dzenlab.adviceandinsult.data.repository

import com.dzenlab.adviceandinsult.data.database.models.*
import com.dzenlab.adviceandinsult.data.database.models.InsultCreate
import com.dzenlab.adviceandinsult.data.database.storage.InsultDatabaseStorage
import com.dzenlab.adviceandinsult.data.mapper.*
import com.dzenlab.adviceandinsult.data.network.storage.InsultNetworkStorage
import com.dzenlab.adviceandinsult.data.sharepref.storage.InsultSharePrefStorage
import com.dzenlab.adviceandinsult.data.sharepref.models.InsultSharePref
import com.dzenlab.adviceandinsult.models.*
import com.dzenlab.adviceandinsult.repository.InsultRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class InsultRepositoryImpl(private val insultNetworkStorage: InsultNetworkStorage,
                           private val insultSharePrefStorage: InsultSharePrefStorage,
                           private val insultDatabaseStorage: InsultDatabaseStorage
) : InsultRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO


    override suspend fun getInsultFromNetwork(): ResponseNet =
        withContext(ioDispatcher) { insultNetworkStorage.getInsult().toInsultNet() }

    override suspend fun getInsultFromSharePref(): InsultSP =
        withContext(ioDispatcher) { insultSharePrefStorage.getInsult().toInsultSP()}

    override suspend fun saveInsultToSharePref(insultSP: InsultSP) =
        withContext(ioDispatcher) {
            insultSharePrefStorage.saveInsult(InsultSharePref.toInsultSharePref(insultSP)) }

    override suspend fun insertInsultToDatabase(insultCreateDB: InsultCreateDB): InsultIdDB =
        withContext(ioDispatcher) { InsultIdDB(id =
        insultDatabaseStorage.insert(InsultCreate.toInsultCreate(insultCreateDB))) }

    override fun getAllInsultFromDatabase(): Flow<List<InsultGetDB>> =
        insultDatabaseStorage.getAll().flowOn(ioDispatcher)
            .map { it.map { insultGet ->  insultGet.toInsultGetDB() } }

    override fun getInsultCountFromDatabase(): Flow<InsultCountDB> =
        insultDatabaseStorage.getCount().flowOn(ioDispatcher).map { InsultCountDB(it) }

    override suspend fun isExistInsultByNumberToDatabase(insultNumberDB: InsultNumberDB):
            InsultIsExistDB =
        withContext(ioDispatcher) {
            InsultIsExistDB(insultDatabaseStorage.isExistByNumber(insultNumberDB.number)) }

    override suspend fun updateInsultToDatabase(insultUpdateDB: InsultUpdateDB) =
        withContext(ioDispatcher) {
            insultDatabaseStorage.updateInsult(InsultUpdate.toInsultUpdate(insultUpdateDB)) }

    override suspend fun deleteInsultByIdFromDatabase(insultDeleteDB: InsultDeleteDB) =
        withContext(ioDispatcher) {
            insultDatabaseStorage.deleteById(InsultDelete.toInsultDelete(insultDeleteDB)) }
}