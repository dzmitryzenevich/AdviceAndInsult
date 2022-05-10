package com.dzenlab.adviceandinsult.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dzenlab.adviceandinsult.data.constants.DATABASE_VERSION
import com.dzenlab.adviceandinsult.data.database.dao.AdviceDao
import com.dzenlab.adviceandinsult.data.database.dao.InsultDao
import com.dzenlab.adviceandinsult.data.database.entities.AdviceEntity
import com.dzenlab.adviceandinsult.data.database.entities.InsultEntity

@Database(entities = [
    AdviceEntity::class,
    InsultEntity::class
                     ], version = DATABASE_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    internal abstract fun adviceDao(): AdviceDao

    internal abstract fun insultDao(): InsultDao
}