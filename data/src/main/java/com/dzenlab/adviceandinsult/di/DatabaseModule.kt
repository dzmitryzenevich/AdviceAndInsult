package com.dzenlab.adviceandinsult.di

import android.content.Context
import androidx.room.Room
import com.dzenlab.adviceandinsult.data.constants.DATABASE_NAME
import com.dzenlab.adviceandinsult.data.database.storage.AdviceDatabaseStorage
import com.dzenlab.adviceandinsult.data.database.AppDatabase
import com.dzenlab.adviceandinsult.data.database.storage.InsultDatabaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideAdviceDatabaseStorage(appDatabase: AppDatabase): AdviceDatabaseStorage =
        AdviceDatabaseStorage(appDatabase)

    @Provides
    @Singleton
    fun provideInsultDatabaseStorage(appDatabase: AppDatabase): InsultDatabaseStorage =
        InsultDatabaseStorage(appDatabase)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
}