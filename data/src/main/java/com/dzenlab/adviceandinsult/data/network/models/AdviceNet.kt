package com.dzenlab.adviceandinsult.data.network.models

import com.dzenlab.adviceandinsult.data.constants.SLIP_SERIALIZE_NAME
import com.google.gson.annotations.SerializedName

internal class AdviceNet(@SerializedName(SLIP_SERIALIZE_NAME) val slip: SlipNet)