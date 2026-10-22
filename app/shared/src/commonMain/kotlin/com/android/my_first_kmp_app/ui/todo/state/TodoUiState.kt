package com.android.my_first_kmp_app.ui.todo.state

import com.android.my_first_kmp_app.feature.models.todos.Todo

sealed interface TodoUiState {

    data object Loading : TodoUiState

    data class Success(
        val todos: List<Todo>
    ) : TodoUiState

    data class Error(
        val message: String
    ) : TodoUiState
}