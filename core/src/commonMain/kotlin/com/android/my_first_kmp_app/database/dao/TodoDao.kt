package com.android.my_first_kmp_app.database.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update
import com.android.my_first_kmp_app.database.entities.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: TodoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(todos: List<TodoEntity>)

    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getTodoById(id: Int): TodoEntity?

    @Query("SELECT * FROM todos")
    fun getAllTodos(): Flow<List<TodoEntity>>

    @Update
    suspend fun update(todo: TodoEntity)

    @Delete
    suspend fun delete(todo: TodoEntity)

    @Query("DELETE FROM todos WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM todos")
    suspend fun deleteAll()
}
