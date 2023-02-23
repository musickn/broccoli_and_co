package com.broccoli.bnc.data.common

import com.broccoli.bnc.R
import retrofit2.HttpException

object RequestErrorHandler {
    private const val HTTP_CODE_CLIENT_START = 400
    private const val HTTP_CODE_CLIENT_END = 499
    private const val HTTP_CODE_SERVER_START = 500
    private const val HTTP_CODE_SERVER_END = 599

    fun getRequestError(throwable: Throwable): RemoteSourceException {
        return when (throwable) {
            is HttpException -> {
                handleHttpException(throwable)
            }
            else -> {
                RemoteSourceException.Unexpected(R.string.error_unexpected_message)
            }
        }
    }

    private fun handleHttpException(httpException: HttpException): RemoteSourceException {
        return when (httpException.code()) {
            in HTTP_CODE_CLIENT_START..HTTP_CODE_CLIENT_END -> {
                RemoteSourceException.Client(R.string.error_client_unexpected_message)
            }
            in HTTP_CODE_SERVER_START..HTTP_CODE_SERVER_END -> {
                RemoteSourceException.Server(R.string.error_server_unexpected_message)
            }
            else -> {
                RemoteSourceException.Unexpected(R.string.error_unexpected_message)
            }
        }
    }
}
