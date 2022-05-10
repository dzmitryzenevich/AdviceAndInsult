package com.dzenlab.adviceandinsult.data.mapper

import com.dzenlab.adviceandinsult.data.network.models.InsultNet
import com.dzenlab.adviceandinsult.models.ResponseNet

internal fun retrofit2.Response<InsultNet>.toInsultNet(): ResponseNet =
    if(this.isSuccessful) { this.body()?.let {
        ResponseNet.Success(
            com.dzenlab.adviceandinsult.models.InsultNet(
            number = it.number,
            insult = it.insult,
            comment = it.comment)) } ?: ResponseNet.Error(error = "Insult is null")
    } else { ResponseNet.Error(error = "Insult is null") }