package com.dzenlab.adviceandinsult.data.network.api

import com.dzenlab.adviceandinsult.data.network.models.AdviceNet
import retrofit2.Response
import retrofit2.http.GET

internal interface AdviceApi {

    @GET("https://api.adviceslip.com/advice")
    suspend fun getAdvice(): Response<AdviceNet>
}