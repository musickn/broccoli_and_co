package com.broccoli.bnc.data.datasource.remote

import com.broccoli.bnc.data.request.EnrollmentRequest
import com.broccoli.bnc.util.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST(Constants.ENROLL_USER)
    suspend fun doEnroll(@Body body: EnrollmentRequest): Response<Unit>
}
