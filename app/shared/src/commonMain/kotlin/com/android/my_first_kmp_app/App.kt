package com.android.my_first_kmp_app

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.my_first_kmp_app.di.coreModuleList
import com.android.my_first_kmp_app.di.networkModule
import com.android.my_first_kmp_app.di.vmModule
import com.android.my_first_kmp_app.ui.MyFirstKMPApp
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration

@Composable
@Preview
fun App() {
    KoinApplication(
        configuration = koinConfiguration {
            modules(vmModule)
            modules(networkModule)
            modules(coreModuleList)
        },
    ) {
        MyFirstKMPApp()
    }
}