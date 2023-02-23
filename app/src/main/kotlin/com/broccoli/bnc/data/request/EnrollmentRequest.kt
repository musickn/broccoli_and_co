package com.broccoli.bnc.data.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class EnrollmentRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String
)
