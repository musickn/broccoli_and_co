package com.broccoli.bnc.data.datasource.remote

import com.broccoli.bnc.data.common.Result
import com.broccoli.bnc.data.request.EnrollmentRequest

interface RemoteDataSource {
    suspend fun doEnroll(body: EnrollmentRequest): Result<Unit>
}
