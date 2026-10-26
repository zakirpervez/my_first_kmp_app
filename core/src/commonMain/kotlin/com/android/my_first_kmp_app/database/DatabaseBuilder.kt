package com.android.my_first_kmp_app.database

import androidx.room3.RoomDatabase
import androidx.sqlite.SQLiteDriver
import kotlinx.coroutines.Dispatchers

expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>
expect fun getSQLiteDriver(): SQLiteDriver

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>,
): AppDatabase {
    return builder
        .setDriver(getSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.Default)
        .build()
}
