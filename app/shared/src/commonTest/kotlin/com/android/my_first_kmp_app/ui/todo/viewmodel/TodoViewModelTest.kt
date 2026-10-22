package com.android.my_first_kmp_app.ui.todo.viewmodel

import com.android.my_first_kmp_app.feature.models.todos.Todo
import com.android.my_first_kmp_app.network.repository.CoreRepository
import com.android.my_first_kmp_app.ui.todo.state.TodoUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class TodoViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchTodos_whileLoading_uiStateIsLoading() = runTest {
        val deferred = CompletableDeferred<List<Todo>>()
        val repository = FakeCoreRepository(suspendingResultProvider = { deferred.await() })

        val viewModel = TodoViewModel(repository)

        assertEquals(TodoUiState.Loading, viewModel.uiState.value)

        val sampleTodos = listOf(Todo(id = 1, title = "Task 1", completed = false))
        deferred.complete(sampleTodos)

        assertEquals(TodoUiState.Success(sampleTodos), viewModel.uiState.value)
    }

    @Test
    fun fetchTodos_success_updatesUiStateToSuccessWithTodoList() = runTest {
        val sampleTodos = listOf(
            Todo(id = 1, title = "Task 1", completed = false, userId = 1),
            Todo(id = 2, title = "Task 2", completed = true, userId = 1)
        )
        val repository = FakeCoreRepository(resultProvider = { sampleTodos })

        val viewModel = TodoViewModel(repository)

        val expectedState = TodoUiState.Success(sampleTodos)
        assertEquals(expectedState, viewModel.uiState.value)
        assertEquals(1, repository.fetchTodosCallCount)
    }

    @Test
    fun fetchTodos_success_emptyList_updatesUiStateToSuccessWithEmptyList() = runTest {
        val repository = FakeCoreRepository(resultProvider = { emptyList() })

        val viewModel = TodoViewModel(repository)

        val expectedState = TodoUiState.Success(emptyList())
        assertEquals(expectedState, viewModel.uiState.value)
        assertEquals(1, repository.fetchTodosCallCount)
    }

    @Test
    fun fetchTodos_generalException_withMessage_updatesUiStateToErrorWithMessage() = runTest {
        val errorMessage = "Failed to connect to server"
        val repository = FakeCoreRepository(exceptionToThrow = RuntimeException(errorMessage))

        val viewModel = TodoViewModel(repository)

        val expectedState = TodoUiState.Error(errorMessage)
        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun fetchTodos_generalException_nullMessage_updatesUiStateToErrorWithDefaultMessage() = runTest {
        val repository = FakeCoreRepository(exceptionToThrow = RuntimeException(null as String?))

        val viewModel = TodoViewModel(repository)

        val expectedState = TodoUiState.Error("Unable to load todos. Please try again.")
        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun fetchTodos_cancellationException_withMessage_updatesUiStateToErrorWithMessage() = runTest {
        val cancellationMsg = "Coroutines job was cancelled"
        val repository = FakeCoreRepository(exceptionToThrow = CancellationException(cancellationMsg))

        val viewModel = TodoViewModel(repository)

        val expectedState = TodoUiState.Error(cancellationMsg)
        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun fetchTodos_cancellationException_nullMessage_updatesUiStateToErrorWithDefaultMessage() = runTest {
        val repository = FakeCoreRepository(exceptionToThrow = CancellationException(null as String?))

        val viewModel = TodoViewModel(repository)

        val expectedState = TodoUiState.Error("Unable to load todos. Please try again.")
        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun fetchTodos_calledManually_retriggersRepositoryAndUpdatesState() = runTest {
        var callCount = 0
        val repository = FakeCoreRepository(resultProvider = {
            callCount++
            if (callCount == 1) {
                listOf(Todo(id = 1, title = "Initial Todo", completed = false))
            } else {
                listOf(
                    Todo(id = 1, title = "Initial Todo", completed = false),
                    Todo(id = 2, title = "New Todo", completed = true)
                )
            }
        })

        val viewModel = TodoViewModel(repository)
        assertEquals(1, repository.fetchTodosCallCount)
        val initialSuccessState = viewModel.uiState.value as TodoUiState.Success
        assertEquals(1, initialSuccessState.todos.size)

        viewModel.fetchTodos()
        assertEquals(2, repository.fetchTodosCallCount)
        val secondSuccessState = viewModel.uiState.value as TodoUiState.Success
        assertEquals(2, secondSuccessState.todos.size)
    }

    private class FakeCoreRepository(
        private val resultProvider: (() -> List<Todo>)? = null,
        private val suspendingResultProvider: (suspend () -> List<Todo>)? = null,
        private val exceptionToThrow: Throwable? = null
    ) : CoreRepository(todoAPIClient = null) {

        var fetchTodosCallCount = 0
            private set

        override suspend fun fetchTodos(): List<Todo> {
            fetchTodosCallCount++
            exceptionToThrow?.let { throw it }
            suspendingResultProvider?.let { return it() }
            return resultProvider?.invoke() ?: emptyList()
        }
    }
}
