package com.android.my_first_kmp_app.di

import com.android.my_first_kmp_app.database.AppDatabase
import com.android.my_first_kmp_app.database.dao.TodoDao
import com.android.my_first_kmp_app.database.getDatabaseBuilder
import com.android.my_first_kmp_app.database.getRoomDatabase
import org.koin.dsl.module

val databaseModule = module {
    try {
        val database = getRoomDatabase(getDatabaseBuilder())
        single<AppDatabase> { database }
        single<TodoDao> { database.todoDao() }
    } catch (_: Throwable) {
        // Platform or environment where database is not supported
    }
}
