package com.android.my_first_kmp_app.database

import android.content.Context
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("my_first_kmp_app.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath,
    )
}

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    error("Call getDatabaseBuilder(context) or pass RoomDatabase.Builder<AppDatabase> via Koin on Android")
}

actual fun getSQLiteDriver(): SQLiteDriver = BundledSQLiteDriver()
