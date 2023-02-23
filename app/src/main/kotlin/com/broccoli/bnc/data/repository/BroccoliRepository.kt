package com.broccoli.bnc.data.repository

import com.broccoli.bnc.data.common.Result
import com.broccoli.bnc.data.datasource.remote.RemoteDataSource
import com.broccoli.bnc.data.request.EnrollmentRequest
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class BroccoliRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun doEnroll(body: EnrollmentRequest): Flow<Result<Unit>> {
        return flow {
            remoteDataSource.doEnroll(body).run {
                when (this) {
                    is Result.Success -> emit(Result.Success(response))
                    is Result.Error -> emit(Result.Error(exception))
                }
            }
        }.onStart {
            emit(Result.Loading)
        }
    }
}
