package com.android.my_first_kmp_app.ui.signup.model

data class SignUpRequest(
    val username: String,
    val fullName: String,
    val email: String,
    val password: String,
    val phoneNumber: String?
)