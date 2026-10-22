package com.android.my_first_kmp_app.ui.todo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.my_first_kmp_app.network.repository.CoreRepository
import com.android.my_first_kmp_app.ui.todo.state.TodoUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoViewModel(val coreRepository: CoreRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<TodoUiState>(TodoUiState.Loading)
    val uiState: StateFlow<TodoUiState> = _uiState.asStateFlow()

    init {
        fetchTodos()
    }

    fun fetchTodos() {
        viewModelScope.launch {
            _uiState.value = TodoUiState.Loading
            try {
                val todos = coreRepository.fetchTodos()
                _uiState.value = TodoUiState.Success(todos)
            } catch (exception: CancellationException) {
                _uiState.value = TodoUiState.Error(
                    exception.message ?: "Unable to load todos. Please try again."
                )
            } catch (exception: Exception) {
                _uiState.value = TodoUiState.Error(
                    exception.message ?: "Unable to load todos. Please try again."
                )
            }
        }
    }
}