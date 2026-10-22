package com.android.my_first_kmp_app.ui.signup

import MToolbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.my_first_kmp_app.ui.theme.dimen.rememberScreenDimens
import my_first_kmp_app.app.shared.generated.resources.Res
import my_first_kmp_app.app.shared.generated.resources.signupTitle
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel,
) {
    val signUpState by viewModel.uiState.collectAsState()
    val dimens = rememberScreenDimens()

    Scaffold(
        modifier = Modifier.fillMaxSize().safeDrawingPadding()
            .background(color = MaterialTheme.colorScheme.background),
        topBar = {
            MToolbar(title = stringResource(Res.string.signupTitle), onBackClick = {
                navController.popBackStack()
            })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(dimens.signUpChildMargin)
        ) {
            Text(
                text = "Create account",
                style = MaterialTheme.typography.headlineMedium
            )
            OutlinedTextField(
                value = signUpState.username,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                onValueChange = viewModel::onUsernameChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email or Username") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )
            OutlinedTextField(
                value = signUpState.fullName,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                onValueChange = viewModel::onFullNameChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Full Name") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.PersonName
                )
            )
            OutlinedTextField(
                value = signUpState.email,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                onValueChange = viewModel::onEmailChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )
            OutlinedTextField(
                value = signUpState.phoneNumber,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                onValueChange = viewModel::onPhoneNumberChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Phone No") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                )
            )
            OutlinedTextField(
                value = signUpState.password,
                onValueChange = viewModel::onPasswordChange,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = if (signUpState.passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    TextButton(
                        onClick = viewModel::togglePasswordVisibility
                    ) {
                        Text(
                            text = if (signUpState.passwordVisible) "Hide" else "Show"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )
            OutlinedTextField(
                value = signUpState.confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Confirm Password") },
                singleLine = true,
                visualTransformation = if (signUpState.confirmPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    TextButton(
                        onClick = viewModel::toggleConfirmPasswordVisibility
                    ) {
                        Text(
                            text = if (signUpState.confirmPasswordVisible) "Hide" else "Show"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )
            Spacer(modifier = Modifier.size(dimens.signUpChildMargin))
            Button(
                onClick = viewModel::validateAndCreateRequest,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = signUpState.isFormValid && !signUpState.isLoading
            ) {
                Text("Sign Up")
            }
        }
    }
}