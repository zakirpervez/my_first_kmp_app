package com.android.my_first_kmp_app.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.my_first_kmp_app.navigation.DetailRoute
import com.android.my_first_kmp_app.navigation.HomeRoute
import com.android.my_first_kmp_app.navigation.LoginRoute
import com.android.my_first_kmp_app.navigation.SignupRoute
import com.android.my_first_kmp_app.navigation.SplashRoute
import com.android.my_first_kmp_app.navigation.TodoRoute
import com.android.my_first_kmp_app.ui.detail.DetailScreen
import com.android.my_first_kmp_app.ui.home.HomeScreen
import com.android.my_first_kmp_app.ui.login.LoginScreen
import com.android.my_first_kmp_app.ui.signup.SignUpScreen
import com.android.my_first_kmp_app.ui.splash.SplashScreen
import com.android.my_first_kmp_app.ui.theme.DarkColorScheme
import com.android.my_first_kmp_app.ui.theme.LightColorScheme
import com.android.my_first_kmp_app.ui.todo.TodoScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun MyFirstKMPApp(darkTheme: Boolean = isSystemInDarkTheme()) {
    MaterialTheme(colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = SplashRoute,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
        ) {
            composable<SplashRoute> {
                SplashScreen(navController = navController)
            }
            composable<LoginRoute> {
                LoginScreen(navController = navController)
            }
            composable<SignupRoute> {
                SignUpScreen(navController = navController, viewModel = koinViewModel())
            }
            composable<HomeRoute> {
                HomeScreen(navController = navController)
            }
            composable<DetailRoute> {
                DetailScreen(navController = navController)
            }
            composable<TodoRoute> {
                TodoScreen(navController = navController, viewModel = koinViewModel())
            }
        }
    }
}

@Composable
@Preview
fun MyFirstKMPAppPreview() {
    MyFirstKMPApp()
}