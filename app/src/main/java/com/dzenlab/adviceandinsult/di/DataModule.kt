package com.dzenlab.adviceandinsult.di

import com.dzenlab.adviceandinsult.data.database.storage.AdviceDatabaseStorage
import com.dzenlab.adviceandinsult.data.database.storage.InsultDatabaseStorage
import com.dzenlab.adviceandinsult.data.network.storage.AdviceNetworkStorage
import com.dzenlab.adviceandinsult.data.network.storage.InsultNetworkStorage
import com.dzenlab.adviceandinsult.data.repository.AdviceRepositoryImpl
import com.dzenlab.adviceandinsult.data.repository.InsultRepositoryImpl
import com.dzenlab.adviceandinsult.data.sharepref.storage.AdviceSharePrefStorage
import com.dzenlab.adviceandinsult.data.sharepref.storage.InsultSharePrefStorage
import com.dzenlab.adviceandinsult.repository.AdviceRepository
import com.dzenlab.adviceandinsult.repository.InsultRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideAdviceRepository(adviceNetworkStorage: AdviceNetworkStorage,
                                adviceSharePrefStorage: AdviceSharePrefStorage,
                                adviceDatabaseStorage: AdviceDatabaseStorage
    ): AdviceRepository =
        AdviceRepositoryImpl(adviceNetworkStorage, adviceSharePrefStorage, adviceDatabaseStorage)

    @Provides
    @Singleton
    fun provideInsultRepository(insultNetworkStorage: InsultNetworkStorage,
                                insultSharePrefStorage: InsultSharePrefStorage,
                                insertDatabaseStorage: InsultDatabaseStorage
    ): InsultRepository =
        InsultRepositoryImpl(insultNetworkStorage, insultSharePrefStorage, insertDatabaseStorage)
}