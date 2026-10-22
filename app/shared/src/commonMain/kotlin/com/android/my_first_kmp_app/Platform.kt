package com.android.my_first_kmp_app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform