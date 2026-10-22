
package com.android.my_first_kmp_app.ui.theme.dimen

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass

data class ScreenDimens(
    val screenMaxWidth: Dp,
    val signUpChildMargin: Dp = 8.dp,
)

private object DimenValues {
    val phone = ScreenDimens(
        screenMaxWidth = 480.dp,
    )

    val tablet = ScreenDimens(
        screenMaxWidth = 900.dp,
    )

    val desktop = ScreenDimens(
        screenMaxWidth = 1200.dp,
    )
}

@Composable
fun rememberScreenDimens(): ScreenDimens {
    val windowSizeClass =
        currentWindowAdaptiveInfoV2().windowSizeClass

    return remember(windowSizeClass) {
        when {
            windowSizeClass.isWidthAtLeastBreakpoint(
                WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND
            ) -> DimenValues.desktop

            windowSizeClass.isWidthAtLeastBreakpoint(
                WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND
            ) -> DimenValues.tablet

            else -> DimenValues.phone
        }
    }
}
