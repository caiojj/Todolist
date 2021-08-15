package br.com.todolist.datasource.repository

import br.com.todolist.App
import br.com.todolist.datasource.model.Task

class TaskRepository {
    suspend fun getList() = App.dataBase?.taskDao()?.getAll()

    suspend fun insert(task: Task) = App.dataBase?.taskDao()?.insert(task)

    suspend fun update(task: Task) = App.dataBase?.taskDao()?.update(task)

    suspend fun delete(task: Task) = App.dataBase?.taskDao()?.delete(task)
}