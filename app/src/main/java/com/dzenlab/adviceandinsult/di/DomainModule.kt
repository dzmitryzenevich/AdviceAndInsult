package com.dzenlab.adviceandinsult.di

import com.dzenlab.adviceandinsult.repository.AdviceRepository
import com.dzenlab.adviceandinsult.repository.InsultRepository
import com.dzenlab.adviceandinsult.usecase.advice.*
import com.dzenlab.adviceandinsult.usecase.insult.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetAdviceFromNetworkUseCase(adviceRepository: AdviceRepository):
            GetAdviceFromNetworkUseCase = GetAdviceFromNetworkUseCase(adviceRepository)

    @Provides
    fun provideGetAdviceFromSharePrefUseCase(adviceRepository: AdviceRepository):
            GetAdviceFromSharePrefUseCase = GetAdviceFromSharePrefUseCase(adviceRepository)

    @Provides
    fun provideSaveAdviceToSharePrefUseCase(adviceRepository: AdviceRepository):
            SaveAdviceToSharePrefUseCase = SaveAdviceToSharePrefUseCase(adviceRepository)

    @Provides
    fun provideGetAllAdviceFromDatabase(adviceRepository: AdviceRepository):
            GetAllAdviceFromDatabaseUseCase = GetAllAdviceFromDatabaseUseCase(adviceRepository)

    @Provides
    fun provideGetAdviceCountFromDatabase(adviceRepository: AdviceRepository):
            GetAdviceCountFromDatabaseUseCase = GetAdviceCountFromDatabaseUseCase(adviceRepository)

    @Provides
    fun provideIsExistAdviceToDatabaseUseCase(adviceRepository: AdviceRepository):
            IsExistAdviceToDatabaseUseCase = IsExistAdviceToDatabaseUseCase(adviceRepository)

    @Provides
    fun provideInsertAdviceToDatabaseUseCase(adviceRepository: AdviceRepository):
            InsertAdviceToDatabaseUseCase = InsertAdviceToDatabaseUseCase(adviceRepository)

    @Provides
    fun provideDeleteAdviceByIdFromDatabaseUseCase(adviceRepository: AdviceRepository):
            DeleteAdviceByIdFromDatabaseUseCase =
        DeleteAdviceByIdFromDatabaseUseCase(adviceRepository)

    @Provides
    fun provideDeleteAdvicesByIdsFromDatabaseUseCase(adviceRepository: AdviceRepository):
            DeleteAdvicesByIdsFromDatabaseUseCase =
        DeleteAdvicesByIdsFromDatabaseUseCase(adviceRepository)

    @Provides
    fun provideGetInsultFromNetworkUseCase(insultRepository: InsultRepository):
            GetInsultFromNetworkUseCase = GetInsultFromNetworkUseCase(insultRepository)

    @Provides
    fun provideGetInsultFromSharePrefUseCase(insultRepository: InsultRepository):
            GetInsultFromSharePrefUseCase = GetInsultFromSharePrefUseCase(insultRepository)

    @Provides
    fun provideSaveInsultToSharePrefUseCase(insultRepository: InsultRepository):
            SaveInsultToSharePrefUseCase = SaveInsultToSharePrefUseCase(insultRepository)

    @Provides
    fun provideGetAllInsultFromDatabase(insultRepository: InsultRepository):
            GetAllInsultFromDatabaseUseCase = GetAllInsultFromDatabaseUseCase(insultRepository)

    @Provides
    fun provideGetInsultCountFromDatabase(insultRepository: InsultRepository):
            GetInsultCountFromDatabaseUseCase = GetInsultCountFromDatabaseUseCase(insultRepository)

    @Provides
    fun provideIsExistInsultToDatabaseUseCase(insultRepository: InsultRepository):
            IsExistInsultToDatabaseUseCase = IsExistInsultToDatabaseUseCase(insultRepository)

    @Provides
    fun provideInsertInsultToDatabaseUseCase(insultRepository: InsultRepository):
            InsertInsultToDatabaseUseCase = InsertInsultToDatabaseUseCase(insultRepository)

    @Provides
    fun provideUpdateInsultToDatabaseUseCase(insultRepository: InsultRepository):
            UpdateInsultToDatabaseUseCase = UpdateInsultToDatabaseUseCase(insultRepository)

    @Provides
    fun provideDeleteInsultByIdFromDatabaseUseCase(insultRepository: InsultRepository):
            DeleteInsultByIdFromDatabaseUseCase =
        DeleteInsultByIdFromDatabaseUseCase(insultRepository)
}