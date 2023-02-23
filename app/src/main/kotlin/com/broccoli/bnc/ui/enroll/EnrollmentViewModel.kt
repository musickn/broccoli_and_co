package com.broccoli.bnc.ui.enroll

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.broccoli.bnc.data.common.Result
import com.broccoli.bnc.data.repository.BroccoliRepository
import com.broccoli.bnc.data.request.EnrollmentRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class EnrollmentViewModel @Inject constructor(private var broccoliRepository: BroccoliRepository) :
    ViewModel() {
    private val _resultEnrollment = MutableStateFlow<Result<Unit>>(Result.Initial)
    val resultEnrollment: StateFlow<Result<Unit>> = _resultEnrollment.asStateFlow()

    fun doEnroll(body: EnrollmentRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            broccoliRepository.doEnroll(body).collect { response: Result<Unit> ->
                _resultEnrollment.value = response
            }
        }
    }
}
