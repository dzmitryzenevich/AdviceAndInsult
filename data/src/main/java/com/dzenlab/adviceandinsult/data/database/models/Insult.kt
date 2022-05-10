package com.dzenlab.adviceandinsult.data.database.models

import androidx.room.ColumnInfo
import com.dzenlab.adviceandinsult.data.constants.*

internal data class InsultCreate(@ColumnInfo(name = NUMBER_COLUMN_NAME) val number: Int,
                                 @ColumnInfo(name = INSULT_COLUMN_NAME) val insult: String,
                                 @ColumnInfo(name = COMMENT_COLUMN_NAME) val comment: String) {
    companion object }

internal data class InsultGet(@ColumnInfo(name = ID_COLUMN_NAME) val id: Long,
                              @ColumnInfo(name = NUMBER_COLUMN_NAME) val number: Int,
                              @ColumnInfo(name = INSULT_COLUMN_NAME) val insult: String,
                              @ColumnInfo(name = COMMENT_COLUMN_NAME) val comment: String)

internal data class InsultUpdate(@ColumnInfo(name = ID_COLUMN_NAME) val id: Long,
                                 @ColumnInfo(name = INSULT_COLUMN_NAME) val insult: String) {
    companion object }

internal data class InsultDelete(@ColumnInfo(name = ID_COLUMN_NAME) val id: Long) {
    companion object }