package com.dzenlab.adviceandinsult.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dzenlab.adviceandinsult.data.constants.*

@Entity(tableName = INSULT_TABLE_NAME,
    indices = [ Index( value = [ NUMBER_COLUMN_NAME ], unique = true ) ] )
internal data class InsultEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_COLUMN_NAME)
    val id: Long = 0,

    @ColumnInfo(name = NUMBER_COLUMN_NAME)
    val number: Int,

    @ColumnInfo(name = INSULT_COLUMN_NAME)
    val insult: String,

    @ColumnInfo(name = COMMENT_COLUMN_NAME)
    val comment: String
)