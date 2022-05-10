package com.dzenlab.adviceandinsult.data.network.models

import com.dzenlab.adviceandinsult.data.constants.COMMENT_SERIALIZE_NAME
import com.dzenlab.adviceandinsult.data.constants.INSULT_SERIALIZE_NAME
import com.dzenlab.adviceandinsult.data.constants.NUMBER_SERIALIZE_NAME
import com.google.gson.annotations.SerializedName

internal class InsultNet(@SerializedName(NUMBER_SERIALIZE_NAME) val number: Int,
                         @SerializedName(INSULT_SERIALIZE_NAME) val insult: String,
                         @SerializedName(COMMENT_SERIALIZE_NAME) val comment: String)