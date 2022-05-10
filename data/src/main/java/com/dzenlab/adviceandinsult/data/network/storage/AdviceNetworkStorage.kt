package com.dzenlab.adviceandinsult.data.network.storage

import com.dzenlab.adviceandinsult.data.network.api.AdviceApi
import com.dzenlab.adviceandinsult.data.network.models.AdviceNet
import retrofit2.Response
import retrofit2.Retrofit

class AdviceNetworkStorage(private val retrofit: Retrofit) {

    internal suspend fun getAdvice(): Response<AdviceNet> =
        retrofit.create(AdviceApi::class.java).getAdvice()
}