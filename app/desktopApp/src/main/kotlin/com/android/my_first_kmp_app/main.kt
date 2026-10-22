package com.android.my_first_kmp_app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "My_first_kmp_app",
    ) {
        App()
    }
}