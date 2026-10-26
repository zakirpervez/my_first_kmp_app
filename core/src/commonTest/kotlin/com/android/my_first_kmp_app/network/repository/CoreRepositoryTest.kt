package com.android.my_first_kmp_app.network.repository

import com.android.my_first_kmp_app.database.dao.TodoDao
import com.android.my_first_kmp_app.database.entities.TodoEntity
import com.android.my_first_kmp_app.feature.models.todos.Todo
import com.android.my_first_kmp_app.network.clients.TodoAPIClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CoreRepositoryTest {

    private class FakeTodoDao : TodoDao {
        val items = mutableListOf<TodoEntity>()

        override suspend fun insert(todo: TodoEntity): Long {
            items.add(todo)
            return todo.id.toLong()
        }

        override suspend fun insertAll(todos: List<TodoEntity>) {
            items.addAll(todos)
        }

        override suspend fun getTodoById(id: Int): TodoEntity? {
            return items.find { it.id == id }
        }

        override fun getAllTodos(): Flow<List<TodoEntity>> {
            return flowOf(items.toList())
        }

        override suspend fun update(todo: TodoEntity) {
            val index = items.indexOfFirst { it.id == todo.id }
            if (index != -1) items[index] = todo
        }

        override suspend fun delete(todo: TodoEntity) {
            items.removeAll { it.id == todo.id }
        }

        override suspend fun deleteById(id: Int) {
            items.removeAll { it.id == id }
        }

        override suspend fun deleteAll() {
            items.clear()
        }
    }

    private class FakeTodoAPIClient : TodoAPIClient(client = io.ktor.client.HttpClient()) {
        var callCount = 0
        var todosToReturn = listOf(Todo(id = 1, title = "API Todo", completed = false, userId = 1))
        var shouldThrow = false

        override suspend fun fetchTodos(): List<Todo> {
            callCount++
            if (shouldThrow) throw RuntimeException("Network error")
            return todosToReturn
        }
    }

    @Test
    fun fetchTodos_firstTime_fetchesFromApiAndSavesToDb() = runTest {
        val fakeDao = FakeTodoDao()
        val fakeClient = FakeTodoAPIClient()
        val repository = CoreRepository(todoAPIClient = fakeClient, todoDao = fakeDao)

        val todos = repository.fetchTodos()

        assertEquals(1, fakeClient.callCount)
        assertEquals(1, fakeDao.items.size)
        assertEquals("API Todo", todos.first().title)
    }

    @Test
    fun fetchTodos_secondTimeWithin15Min_servesFromDbWithoutApiCall() = runTest {
        val fakeDao = FakeTodoDao()
        val fakeClient = FakeTodoAPIClient()
        val repository = CoreRepository(todoAPIClient = fakeClient, todoDao = fakeDao)

        repository.fetchTodos()
        assertEquals(1, fakeClient.callCount)

        // Second call within 15 mins
        val todos = repository.fetchTodos()
        assertEquals(1, fakeClient.callCount) // API call count remains 1
        assertEquals("API Todo", todos.first().title)
    }

    @Test
    fun fetchTodos_apiFails_fallsBackToDbCache() = runTest {
        val fakeDao = FakeTodoDao()
        fakeDao.insert(TodoEntity(id = 99, title = "Cached Todo", completed = true, userId = 1))

        val fakeClient = FakeTodoAPIClient().apply { shouldThrow = true }
        val repository = CoreRepository(todoAPIClient = fakeClient, todoDao = fakeDao)

        val todos = repository.fetchTodos()

        assertEquals(1, fakeClient.callCount)
        assertEquals(1, todos.size)
        assertEquals("Cached Todo", todos.first().title)
    }
}
