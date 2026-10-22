package com.android.my_first_kmp_app.network.repository

import com.android.my_first_kmp_app.feature.models.todos.Todo
import com.android.my_first_kmp_app.network.clients.TodoAPIClient

open class CoreRepository(val todoAPIClient: TodoAPIClient? = null) {
    open suspend fun fetchTodos(): List<Todo> {
        return todoAPIClient?.fetchTodos() ?: emptyList()
    }
}