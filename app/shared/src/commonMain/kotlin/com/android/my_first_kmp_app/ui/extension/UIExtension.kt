package com.android.my_first_kmp_app.ui.extension

import androidx.navigation.NavController
import com.android.my_first_kmp_app.navigation.AbstractRoutes

fun NavController.popAndNavigate(route: AbstractRoutes) {
    popBackStack()
    navigate(route)
}