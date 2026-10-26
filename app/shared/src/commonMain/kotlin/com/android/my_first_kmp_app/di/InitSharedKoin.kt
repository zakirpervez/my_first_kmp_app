package com.android.my_first_kmp_app.di

import com.android.my_first_kmp_app.network.repository.CoreRepository
import com.android.my_first_kmp_app.ui.signup.SignUpViewModel
import com.android.my_first_kmp_app.ui.todo.viewmodel.TodoViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val networkModule = module {
    single {
        CoreRepository(
            todoAPIClient = get(),
            todoDao = getOrNull(),
        )
    }
}

val vmModule = module {
    viewModel { SignUpViewModel() }
    viewModel { TodoViewModel(coreRepository = get()) }
}

fun initSharedKoin(config: KoinAppDeclaration = {}): KoinApplication {
    return startKoin {
        config()
        modules(networkModule)
        modules(vmModule)
        modules(coreModuleList)
    }
}
