package com.dzenlab.adviceandinsult.data.repository

import com.dzenlab.adviceandinsult.data.database.storage.AdviceDatabaseStorage
import com.dzenlab.adviceandinsult.data.database.models.AdviceCreate
import com.dzenlab.adviceandinsult.data.database.models.AdviceDelete
import com.dzenlab.adviceandinsult.data.database.models.AdviceUpdate
import com.dzenlab.adviceandinsult.data.mapper.*
import com.dzenlab.adviceandinsult.data.network.storage.AdviceNetworkStorage
import com.dzenlab.adviceandinsult.data.sharepref.storage.AdviceSharePrefStorage
import com.dzenlab.adviceandinsult.data.sharepref.models.AdviceSharePref
import com.dzenlab.adviceandinsult.models.*
import com.dzenlab.adviceandinsult.repository.AdviceRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class AdviceRepositoryImpl(private val adviceNetworkStorage: AdviceNetworkStorage,
                           private val adviceSharePrefStorage: AdviceSharePrefStorage,
                           private val adviceDatabaseStorage: AdviceDatabaseStorage
) : AdviceRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO


    override suspend fun getAdviceFromNetwork(): ResponseNet =
        withContext(ioDispatcher) { adviceNetworkStorage.getAdvice().toAdviceNet() }

    override suspend fun getAdviceFromSharePref(): AdviceSP =
        withContext(ioDispatcher) { adviceSharePrefStorage.getAdvice().toAdviceSP()}

    override suspend fun saveAdviceToSharePref(adviceSP: AdviceSP) =
        withContext(ioDispatcher) {
            adviceSharePrefStorage.saveAdvice(AdviceSharePref.toAdviceSharePref(adviceSP)) }

    override suspend fun insertAdviceToDatabase(adviceCreateDB: AdviceCreateDB): AdviceIdDB =
        withContext(ioDispatcher) { AdviceIdDB(id =
        adviceDatabaseStorage.insert(AdviceCreate.toAdviceCreate(adviceCreateDB))) }

    override fun getAllAdviceFromDatabase(): Flow<List<AdviceGetDB>> =
        adviceDatabaseStorage.getAll().flowOn(ioDispatcher)
            .map { it.map { adviceGet ->  adviceGet.toAdviceGetDB() } }

    override fun getAdviceCountFromDatabase(): Flow<AdviceCountDB> =
        adviceDatabaseStorage.getCount().flowOn(ioDispatcher).map { AdviceCountDB(it) }

    override suspend fun isExistAdviceByNumberToDatabase(adviceNumberDB: AdviceNumberDB):
            AdviceIsExistDB =
        withContext(ioDispatcher) {
            AdviceIsExistDB(adviceDatabaseStorage.isExistByNumber(adviceNumberDB.number)) }

    override suspend fun updateAdviceToDatabase(adviceUpdateDB: AdviceUpdateDB) =
        withContext(ioDispatcher) {
            adviceDatabaseStorage.updateAdvice(AdviceUpdate.toAdviceUpdate(adviceUpdateDB)) }

    override suspend fun deleteAdviceByIdFromDatabase(adviceDeleteDB: AdviceDeleteDB) =
        withContext(ioDispatcher) {
            adviceDatabaseStorage.deleteById(AdviceDelete.toAdviceDelete(adviceDeleteDB)) }

    override suspend fun deleteAdvicesByIdsFromDatabase(list: List<AdviceDeleteDB>) =
        withContext(ioDispatcher) {
            adviceDatabaseStorage.deleteAllById(list.map { AdviceDelete(it.id) }) }
}