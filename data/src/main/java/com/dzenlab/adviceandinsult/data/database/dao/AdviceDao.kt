package com.dzenlab.adviceandinsult.data.database.dao

import androidx.room.*
import com.dzenlab.adviceandinsult.data.constants.*
import com.dzenlab.adviceandinsult.data.database.entities.AdviceEntity
import com.dzenlab.adviceandinsult.data.database.models.AdviceCreate
import com.dzenlab.adviceandinsult.data.database.models.AdviceDelete
import com.dzenlab.adviceandinsult.data.database.models.AdviceGet
import com.dzenlab.adviceandinsult.data.database.models.AdviceUpdate
import kotlinx.coroutines.flow.Flow

@Dao
internal interface AdviceDao {

    @Insert(entity = AdviceEntity::class)
    suspend fun insertAdvice(adviceCreate: AdviceCreate): Long

    @Query("SELECT * FROM $ADVICE_TABLE_NAME ORDER BY $ID_COLUMN_NAME DESC")
    fun getAll(): Flow<List<AdviceGet>>

    @Query("SELECT COUNT(*) FROM $ADVICE_TABLE_NAME")
    fun getCount(): Flow<Int>

    @Query("SELECT EXISTS(SELECT * FROM $ADVICE_TABLE_NAME WHERE $NUMBER_COLUMN_NAME = :number) ")
    suspend fun isExistByNumber(number: Int): Boolean

    @Update(entity = AdviceEntity::class)
    suspend fun updateAdvice(adviceUpdate: AdviceUpdate)

    @Delete(entity = AdviceEntity::class)
    suspend fun deleteById(adviceDelete: AdviceDelete)

    @Delete(entity = AdviceEntity::class)
    suspend fun deleteAllById(list: List<AdviceDelete>)
}