package com.dzenlab.adviceandinsult.data.network.api

import com.dzenlab.adviceandinsult.data.network.models.InsultNet
import retrofit2.Response
import retrofit2.http.GET

internal interface InsultApi {

    @GET("https://evilinsult.com/generate_insult.php?lang=ru&type=json")
    suspend fun getInsult(): Response<InsultNet>
}