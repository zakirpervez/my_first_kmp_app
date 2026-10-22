package com.android.my_first_kmp_app.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.my_first_kmp_app.config.CoreConfig
import com.android.my_first_kmp_app.navigation.LoginRoute
import com.android.my_first_kmp_app.navigation.TodoRoute
import com.android.my_first_kmp_app.ui.extension.popAndNavigate
import com.android.my_first_kmp_app.ui.theme.dimen.AppDurationConstant
import com.android.my_first_kmp_app.ui.theme.dimen.rememberScreenDimens
import kotlinx.coroutines.delay
import my_first_kmp_app.app.shared.generated.resources.Res
import my_first_kmp_app.app.shared.generated.resources.multicolor_kmp
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    var counter by remember { mutableIntStateOf(3) }
    val dimens = rememberScreenDimens()

    LaunchedEffect(Unit) {
        while (counter > 0) {
            delay(AppDurationConstant.splashDelay)
            counter--
        }
        //exitAndNavigate(navController)
//        navController.popAndNavigate(TodoRoute)
        navController.navigate(TodoRoute)
    }


    Scaffold(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
        BoxWithConstraints {
            val isLandscape = maxWidth > maxHeight
            val logoFraction = if (isLandscape) 0.3f else 0.5f

            Column(
                modifier = modifier.fillMaxSize().widthIn(dimens.screenMaxWidth),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(logoFraction).aspectRatio(1f),
                    painter = painterResource(Res.drawable.multicolor_kmp),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Welcome to My First KMP App",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = MaterialTheme.colorScheme.primary
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "$counter",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodyLargeEmphasized.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Environment : ${CoreConfig.ENVIRONMENT}",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodyLargeEmphasized.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                )
            }
        }
    }
}

@Preview()
@Composable
fun SplashScreenPhonePreview() {
    val dimens = rememberScreenDimens()
    SplashScreen(
        modifier = Modifier.width(dimens.screenMaxWidth),
        rememberNavController()
    )
}

@Preview(name = "Web Style Preview", widthDp = 360, heightDp = 640, showBackground = true)
@Composable
fun SplashScreenDesktopPreview() {
    val dimens = rememberScreenDimens()
    SplashScreen(
        modifier = Modifier.width(dimens.screenMaxWidth),
        rememberNavController()
    )
}