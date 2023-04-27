package com.gturedi.github.network.model

sealed class Resource<out T> {

    data class Success<T>(val data: T?) : Resource<T>()
    data class Failure(val message: String?) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    object Init : Resource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading[]"
            is Success -> "Success[data: $data]"
            is Failure -> "Failure[error: $message"
            is Init -> "Init[]"
        }
    }
}

