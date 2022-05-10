package com.dzenlab.adviceandinsult.di

import android.content.Context
import android.content.SharedPreferences
import com.dzenlab.adviceandinsult.data.constants.SHARE_PREF_NAME
import com.dzenlab.adviceandinsult.data.sharepref.storage.AdviceSharePrefStorage
import com.dzenlab.adviceandinsult.data.sharepref.storage.InsultSharePrefStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharePrefModule {

    @Provides
    @Singleton
    fun provideAdviceSharePrefStorage(sharedPreferences: SharedPreferences):
            AdviceSharePrefStorage = AdviceSharePrefStorage(sharedPreferences)

    @Provides
    @Singleton
    fun provideInsultSharePrefStorage(sharedPreferences: SharedPreferences):
            InsultSharePrefStorage = InsultSharePrefStorage(sharedPreferences)

    @Provides
    @Singleton
    fun provideSharePref(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE)
}