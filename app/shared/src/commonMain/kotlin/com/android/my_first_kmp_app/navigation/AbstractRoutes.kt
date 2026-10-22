package com.android.my_first_kmp_app.navigation

import kotlinx.serialization.Serializable


@Serializable
sealed class AbstractRoutes

@Serializable
data object SplashRoute : AbstractRoutes()

@Serializable
data object LoginRoute : AbstractRoutes()

@Serializable
data object SignupRoute : AbstractRoutes()

@Serializable
data object HomeRoute : AbstractRoutes()

@Serializable
data object DetailRoute : AbstractRoutes()

@Serializable
data object TodoRoute : AbstractRoutes()