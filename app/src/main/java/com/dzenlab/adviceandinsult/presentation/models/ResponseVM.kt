package com.dzenlab.adviceandinsult.presentation.models

sealed class ResponseVM {

    data class Success<D>(val data: D) : ResponseVM()

    data class Exceptions(val message: String?) : ResponseVM()

    data class Error<E>(val code: E) : ResponseVM()
}