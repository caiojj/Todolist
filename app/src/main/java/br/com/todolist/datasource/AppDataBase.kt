package br.com.todolist.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.todolist.datasource.dao.TaskDao
import br.com.todolist.datasource.model.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}