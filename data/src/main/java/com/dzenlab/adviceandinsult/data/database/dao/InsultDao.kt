package com.dzenlab.adviceandinsult.data.database.dao

import androidx.room.*
import com.dzenlab.adviceandinsult.data.constants.ID_COLUMN_NAME
import com.dzenlab.adviceandinsult.data.constants.INSULT_TABLE_NAME
import com.dzenlab.adviceandinsult.data.constants.NUMBER_COLUMN_NAME
import com.dzenlab.adviceandinsult.data.database.entities.InsultEntity
import com.dzenlab.adviceandinsult.data.database.models.*
import com.dzenlab.adviceandinsult.data.database.models.InsultCreate
import kotlinx.coroutines.flow.Flow

@Dao
internal interface InsultDao {

    @Insert(entity = InsultEntity::class)
    suspend fun insertInsult(insultCreate: InsultCreate): Long

    @Query("SELECT * FROM $INSULT_TABLE_NAME ORDER BY $ID_COLUMN_NAME DESC")
    fun getAll(): Flow<List<InsultGet>>

    @Query("SELECT COUNT(*) FROM $INSULT_TABLE_NAME")
    fun getCount(): Flow<Int>

    @Query("SELECT EXISTS(SELECT * FROM $INSULT_TABLE_NAME WHERE $NUMBER_COLUMN_NAME = :number) ")
    suspend fun isExistByNumber(number: Int): Boolean

    @Update(entity = InsultEntity::class)
    suspend fun updateInsult(insultUpdate: InsultUpdate)

    @Delete(entity = InsultEntity::class)
    suspend fun deleteById(insultDelete: InsultDelete)
}