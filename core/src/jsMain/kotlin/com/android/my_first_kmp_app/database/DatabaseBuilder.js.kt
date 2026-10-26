package com.android.my_first_kmp_app.database

import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.SQLiteDriver

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    return Room.databaseBuilder<AppDatabase>(name = "my_first_kmp_app.db")
}

actual fun getSQLiteDriver(): SQLiteDriver {
    error("Web SQLite driver not configured")
}
