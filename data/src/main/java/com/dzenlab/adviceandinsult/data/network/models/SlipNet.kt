package com.dzenlab.adviceandinsult.data.network.models

import com.dzenlab.adviceandinsult.data.constants.ADVICE_SERIALIZE_NAME
import com.dzenlab.adviceandinsult.data.constants.ID_SERIALIZE_NAME
import com.google.gson.annotations.SerializedName

internal class SlipNet(@SerializedName(ID_SERIALIZE_NAME) val number: Int,
                       @SerializedName(ADVICE_SERIALIZE_NAME) val advice: String)