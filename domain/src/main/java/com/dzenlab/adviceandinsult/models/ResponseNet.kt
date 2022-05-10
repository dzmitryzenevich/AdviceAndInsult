package com.dzenlab.adviceandinsult.models

sealed class ResponseNet {

    data class Success<T>(val data: T) : ResponseNet()

    data class Error(val error: String) : ResponseNet()

    data class Progress(val progress: Int) : ResponseNet()
}