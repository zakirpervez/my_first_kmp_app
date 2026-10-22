package com.android.my_first_kmp_app.ui.signup.model

data class SignUpUiState(
    var username: String = "",
    var fullName: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    var passwordVisible: Boolean = false,
    var confirmPasswordVisible: Boolean = false,
    var validationError: String? = null,
    var serverError: String? = null,
    var isLoading: Boolean = false,
    val isFormValid: Boolean = false,
)