package com.dzenlab.adviceandinsult.data.database.storage

import com.dzenlab.adviceandinsult.data.database.AppDatabase
import com.dzenlab.adviceandinsult.data.database.models.*
import com.dzenlab.adviceandinsult.data.database.models.InsultCreate
import kotlinx.coroutines.flow.Flow

class InsultDatabaseStorage(private val appDatabase: AppDatabase) {

    internal suspend fun insert(insultCreate: InsultCreate): Long =
        appDatabase.insultDao().insertInsult(insultCreate)

    internal fun getAll(): Flow<List<InsultGet>> = appDatabase.insultDao().getAll()

    internal fun getCount(): Flow<Int> = appDatabase.insultDao().getCount()

    internal suspend fun isExistByNumber(number: Int): Boolean =
        appDatabase.insultDao().isExistByNumber(number)

    internal suspend fun updateInsult(insultUpdate: InsultUpdate) =
        appDatabase.insultDao().updateInsult(insultUpdate)

    internal suspend fun deleteById(insultDelete: InsultDelete) =
        appDatabase.insultDao().deleteById(insultDelete)
}