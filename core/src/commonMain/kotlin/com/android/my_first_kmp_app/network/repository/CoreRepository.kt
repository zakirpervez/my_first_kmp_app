package com.android.my_first_kmp_app.network.repository

import com.android.my_first_kmp_app.database.dao.TodoDao
import com.android.my_first_kmp_app.database.entities.TodoEntity
import com.android.my_first_kmp_app.feature.models.todos.Todo
import com.android.my_first_kmp_app.network.clients.TodoAPIClient
import kotlinx.coroutines.flow.firstOrNull
import kotlin.time.Clock

open class CoreRepository(
    val todoAPIClient: TodoAPIClient? = null,
    val todoDao: TodoDao? = null,
) {
    private var lastFetchTimeMs: Long = 0L
    private val syncIntervalMs: Long = 15 * 60 * 1000L // 15 minutes

    open suspend fun fetchTodos(): List<Todo> {
        val currentTimeMs = Clock.System.now().toEpochMilliseconds()
        val isCacheExpired = (currentTimeMs - lastFetchTimeMs) >= syncIntervalMs

        if (isCacheExpired || lastFetchTimeMs == 0L) {
            try {
                val remoteTodos = todoAPIClient?.fetchTodos() ?: emptyList()
                if (remoteTodos.isNotEmpty() && todoDao != null) {
                    val entities = remoteTodos.map { it.toEntity() }
                    // OnConflictStrategy.REPLACE updates conflicting rows with latest API data and prevents duplicates
                    todoDao.insertAll(entities)
                    lastFetchTimeMs = currentTimeMs
                }
            } catch (e: Exception) {
                // Fallback to local database cache if network fails
                val cached = todoDao?.getAllTodos()?.firstOrNull()
                if (!cached.isNullOrEmpty()) {
                    return cached.map { it.toModel() }
                }
                throw e
            }
        }

        // Return from database cache
        val cachedEntities = todoDao?.getAllTodos()?.firstOrNull()
        if (!cachedEntities.isNullOrEmpty()) {
            return cachedEntities.map { it.toModel() }
        }

        // Fetch from API if DB is empty
        val remoteTodos = todoAPIClient?.fetchTodos() ?: emptyList()
        if (remoteTodos.isNotEmpty() && todoDao != null) {
            val entities = remoteTodos.map { it.toEntity() }
            todoDao.insertAll(entities)
            lastFetchTimeMs = currentTimeMs
        }
        return remoteTodos
    }
}

fun Todo.toEntity(): TodoEntity = TodoEntity(
    id = id ?: 0,
    title = title ?: "",
    completed = completed ?: false,
    userId = userId ?: 0,
)

fun TodoEntity.toModel(): Todo = Todo(
    id = id,
    title = title,
    completed = completed,
    userId = userId,
)
