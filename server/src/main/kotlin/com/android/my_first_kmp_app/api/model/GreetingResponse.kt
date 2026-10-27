package com.android.my_first_kmp_app.api.model

import kotlinx.serialization.Serializable

@Serializable
data class GreetingResponse(val status: Int, val message: String)
