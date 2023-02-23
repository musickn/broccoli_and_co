package com.broccoli.bnc.data.common

import com.broccoli.bnc.R
import org.json.JSONObject
import retrofit2.Response

const val serverErrorKey = "errorMessage"

suspend fun <T : Any> handleResponse(
    execute: suspend () -> Response<T>
): Result<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Result.Success(body)
        } else {
            val jsonObject = JSONObject(response.errorBody()!!.string())
            if (jsonObject.has(serverErrorKey)) {
                Result.Error(
                    RemoteSourceException.Server(jsonObject.getString(serverErrorKey))
                )
            } else {
                Result.Error(
                    RemoteSourceException.Unexpected(R.string.error_client_unexpected_message)
                )
            }
        }
    } catch (e: Throwable) {
        Result.Error(RequestErrorHandler.getRequestError(e))
    }
}
