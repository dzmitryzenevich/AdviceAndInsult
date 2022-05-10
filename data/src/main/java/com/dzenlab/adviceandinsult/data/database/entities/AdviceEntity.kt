package com.dzenlab.adviceandinsult.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dzenlab.adviceandinsult.data.constants.ADVICE_COLUMN_NAME
import com.dzenlab.adviceandinsult.data.constants.ADVICE_TABLE_NAME
import com.dzenlab.adviceandinsult.data.constants.ID_COLUMN_NAME
import com.dzenlab.adviceandinsult.data.constants.NUMBER_COLUMN_NAME

@Entity(tableName = ADVICE_TABLE_NAME,
    indices = [ Index( value = [ NUMBER_COLUMN_NAME ], unique = true ) ] )
internal data class AdviceEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_COLUMN_NAME)
    val id: Long = 0,

    @ColumnInfo(name = NUMBER_COLUMN_NAME)
    val number: Int,

    @ColumnInfo(name = ADVICE_COLUMN_NAME)
    val advice: String)