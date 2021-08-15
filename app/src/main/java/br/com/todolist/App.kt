package br.com.todolist

import android.app.Application
import androidx.room.Room
import br.com.todolist.datasource.AppDataBase

class App : Application() {
    companion object {
        var dataBase: AppDataBase? = null
    }

    override fun onCreate() {
        super.onCreate()

        // Room
        dataBase = Room
            .databaseBuilder(this, AppDataBase::class.java, "task-db")
            .build()
    }

}