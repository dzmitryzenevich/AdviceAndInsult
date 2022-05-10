package com.dzenlab.adviceandinsult.data.network.storage

import com.dzenlab.adviceandinsult.data.network.api.InsultApi
import com.dzenlab.adviceandinsult.data.network.models.InsultNet
import retrofit2.Response
import retrofit2.Retrofit

class InsultNetworkStorage(private val retrofit: Retrofit) {

    internal suspend fun getInsult(): Response<InsultNet> =
        retrofit.create(InsultApi::class.java).getInsult()
}