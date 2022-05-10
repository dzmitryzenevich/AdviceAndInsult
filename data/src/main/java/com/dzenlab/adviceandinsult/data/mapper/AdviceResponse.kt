package com.dzenlab.adviceandinsult.data.mapper

import com.dzenlab.adviceandinsult.data.network.models.AdviceNet
import com.dzenlab.adviceandinsult.models.ResponseNet

internal fun retrofit2.Response<AdviceNet>.toAdviceNet(): ResponseNet =
    if(this.isSuccessful) { body()?.let {
        ResponseNet.Success(
            com.dzenlab.adviceandinsult.models.AdviceNet(
            number = it.slip.number, advice = it.slip.advice)) }
        ?: ResponseNet.Error(error = "Advice is null") } else {
            ResponseNet.Error(error = "Advice is null") }
