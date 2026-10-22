package com.android.my_first_kmp_app.ui.signup

import androidx.lifecycle.ViewModel
import com.android.my_first_kmp_app.ui.signup.model.SignUpRequest
import com.android.my_first_kmp_app.ui.signup.model.SignUpUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class SignUpViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun onUsernameChange(value: String) = updateField {
        copy(username = value)
    }

    fun onFullNameChange(value: String) = updateField {
        copy(fullName = value)
    }

    fun onEmailChange(value: String) = updateField {
        copy(email = value)
    }

    fun onPhoneNumberChange(value: String) = updateField {
        copy(phoneNumber = value)
    }

    fun onPasswordChange(value: String) = updateField {
        copy(password = value)
    }

    fun onConfirmPasswordChange(value: String) = updateField {
        copy(confirmPassword = value)
    }

    fun togglePasswordVisibility() {
        _uiState.update {
            it.copy(passwordVisible = !it.passwordVisible)
        }
    }

    fun toggleConfirmPasswordVisibility() {
        _uiState.update {
            it.copy(
                confirmPasswordVisible = !it.confirmPasswordVisible
            )
        }
    }

    private fun updateField(
        update: SignUpUiState.() -> SignUpUiState
    ) {
        _uiState.update {
            it.update().copy(
                validationError = null,
                serverError = null
            )
        }
    }

    fun validateAndCreateRequest(): SignUpRequest? {
        val state = _uiState.value

        val error = when {
            state.username.isBlank() ->
                "Please enter a username."

            state.username.trim().length > 50 ->
                "Username must not exceed 50 characters."

            state.fullName.isBlank() ->
                "Please enter your full name."

            state.fullName.trim().length > 150 ->
                "Full name must not exceed 150 characters."

            state.email.isBlank() ||
                    !state.email.trim().matches(
                        Regex(
                            "^[A-Za-z0-9+_.%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
                        )
                    ) ->
                "Please enter a valid email address."

            state.email.trim().length > 255 ->
                "Email must not exceed 255 characters."

            state.phoneNumber.length > 20 ->
                "Phone number must not exceed 20 characters."

            state.password.length < 8 ->
                "Password must contain at least 8 characters."

            state.password != state.confirmPassword ->
                "Passwords do not match."

            else -> null
        }

        if (error != null) {
            _uiState.update { it.copy(validationError = error) }
            return null
        }

        return SignUpRequest(
            username = state.username.trim(),
            fullName = state.fullName.trim(),
            email = state.email.trim(),
            password = state.password,
            phoneNumber = state.phoneNumber.trim()
                .takeIf { it.isNotEmpty() }
        )
    }

    fun setLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    fun setServerError(error: String?) {
        _uiState.update { it.copy(serverError = error) }
    }

    private fun isFormComplete(
        state: SignUpUiState
    ): Boolean {
        return state.username.isNotBlank() &&
                state.fullName.isNotBlank() &&
                state.email.isNotBlank() &&
                state.phoneNumber.isNotBlank() &&
                state.password.isNotBlank() &&
                state.confirmPassword.isNotBlank()
    }
}