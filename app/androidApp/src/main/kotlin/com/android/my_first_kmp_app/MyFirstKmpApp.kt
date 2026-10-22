package com.android.my_first_kmp_app

import android.app.Application
import com.android.my_first_kmp_app.di.initSharedKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger


class MyFirstKmpApp : Application() {
    override fun onCreate() {
        super.onCreate()

//        initSharedKoin {
//            androidLogger()
//            androidContext(this@MyFirstKmpApp)
//        }
    }
}