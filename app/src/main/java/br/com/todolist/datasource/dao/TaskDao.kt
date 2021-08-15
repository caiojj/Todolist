package br.com.todolist.datasource.dao

import androidx.room.*
import br.com.todolist.datasource.model.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    suspend fun getAll(): List<Task>

    @Insert
    suspend fun insert(vararg task: Task)

    @Update
    suspend fun update(vararg task: Task): Int

    @Delete
    suspend fun delete(task: Task)
}