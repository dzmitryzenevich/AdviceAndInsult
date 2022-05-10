package com.dzenlab.adviceandinsult.data.database.models

import androidx.room.ColumnInfo
import com.dzenlab.adviceandinsult.data.constants.ADVICE_COLUMN_NAME
import com.dzenlab.adviceandinsult.data.constants.ID_COLUMN_NAME
import com.dzenlab.adviceandinsult.data.constants.NUMBER_COLUMN_NAME

internal data class AdviceCreate(@ColumnInfo(name = NUMBER_COLUMN_NAME) val number: Int,
                                 @ColumnInfo(name = ADVICE_COLUMN_NAME) val advice: String) {
    companion object }

internal data class AdviceGet(@ColumnInfo(name = ID_COLUMN_NAME) val id: Long,
                              @ColumnInfo(name = NUMBER_COLUMN_NAME) val number: Int,
                              @ColumnInfo(name = ADVICE_COLUMN_NAME) val advice: String)

internal data class AdviceUpdate(@ColumnInfo(name = ID_COLUMN_NAME) val id: Long,
                                 @ColumnInfo(name = ADVICE_COLUMN_NAME) val advice: String) {
    companion object }

internal data class AdviceDelete(@ColumnInfo(name = ID_COLUMN_NAME) val id: Long) {
    companion object }