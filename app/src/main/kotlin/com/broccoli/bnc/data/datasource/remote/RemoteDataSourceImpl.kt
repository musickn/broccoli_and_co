package com.broccoli.bnc.data.datasource.remote

import com.broccoli.bnc.data.common.Result
import com.broccoli.bnc.data.common.handleResponse
import com.broccoli.bnc.data.request.EnrollmentRequest

class RemoteDataSourceImpl(private val apis: ApiService) : RemoteDataSource {

    override suspend fun doEnroll(body: EnrollmentRequest): Result<Unit> =
        handleResponse {
            apis.doEnroll(body)
        }
}
