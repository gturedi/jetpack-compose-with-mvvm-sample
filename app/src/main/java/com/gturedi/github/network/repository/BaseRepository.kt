package com.gturedi.github.network.repository

import com.gturedi.github.network.model.Resource
import org.koin.core.component.KoinComponent
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRepository : KoinComponent {

    fun evaluateError(e: Throwable) =
        Resource.Failure(getErrorMessage(e))

    //todo use context.getString()
    private fun getErrorMessage(e: Throwable) =
        when (e) {
            is UnknownHostException,
            is ConnectException,
            is SocketException -> "Please check your connection"
            is SocketTimeoutException -> "Looks like the server is taking to long to respond, please try again in sometime"
            is HttpException -> {
                when (e.code()) {
                    401 -> "Please login again"
                    404 -> "Resource not found"
                    422 -> "There is no content"
                    429 -> "Daily call quota exceeded. Please try tomorrow"
                    else -> {
                        val json = e.response()?.errorBody()?.string()
                        json
                    }
                }
            }
            else -> e.message
        }
}