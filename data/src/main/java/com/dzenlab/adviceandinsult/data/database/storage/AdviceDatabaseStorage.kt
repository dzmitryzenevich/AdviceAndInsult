package com.dzenlab.adviceandinsult.data.database.storage

import com.dzenlab.adviceandinsult.data.database.AppDatabase
import com.dzenlab.adviceandinsult.data.database.entities.AdviceEntity
import com.dzenlab.adviceandinsult.data.database.models.AdviceCreate
import com.dzenlab.adviceandinsult.data.database.models.AdviceDelete
import com.dzenlab.adviceandinsult.data.database.models.AdviceGet
import com.dzenlab.adviceandinsult.data.database.models.AdviceUpdate
import kotlinx.coroutines.flow.Flow

class AdviceDatabaseStorage(private val appDatabase: AppDatabase) {

    internal suspend fun insert(adviceCreate: AdviceCreate): Long =
        appDatabase.adviceDao().insertAdvice(adviceCreate)

    internal fun getAll(): Flow<List<AdviceGet>> = appDatabase.adviceDao().getAll()

    internal fun getCount(): Flow<Int> = appDatabase.adviceDao().getCount()

    internal suspend fun isExistByNumber(number: Int): Boolean =
        appDatabase.adviceDao().isExistByNumber(number)

    internal suspend fun updateAdvice(adviceUpdate: AdviceUpdate) =
        appDatabase.adviceDao().updateAdvice(adviceUpdate)

    internal suspend fun deleteById(adviceDelete: AdviceDelete) =
        appDatabase.adviceDao().deleteById(adviceDelete)

    internal suspend fun deleteAllById(list: List<AdviceDelete>) =
        appDatabase.adviceDao().deleteAllById(list)
}