package dev.sagar.assigmenthub.utils

sealed class ResponseModel<out T> {

    data class Loading(val message: String? = "") : ResponseModel<Nothing>()
    data class Success<T>(val response: T, val message: String = "") : ResponseModel<T>()
    data class Error<T>(val error: Throwable?, val message: String = "") : ResponseModel<T>()
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}
