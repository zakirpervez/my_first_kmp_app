package com.android.my_first_kmp_app.di

import com.android.my_first_kmp_app.network.HttpClientFactory
import com.android.my_first_kmp_app.network.clients.TodoAPIClient
import com.android.my_first_kmp_app.network.repository.CoreRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val factoryModules = module {
    single {
        HttpClientFactory.create()
    }
}

val apiClientModule = module {
    single {
        TodoAPIClient(
            client = get()
        )
    }
}

val repositoryModule = module {
    single {
        CoreRepository(
            todoAPIClient = get()
        )
    }
}

val coreModuleList: List<Module> = listOf(
    factoryModules,
    apiClientModule,
    repositoryModule,
    databaseModule,
)
