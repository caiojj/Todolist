package br.com.todolist.datasource.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.todolist.datasource.model.Task
import br.com.todolist.datasource.repository.TaskRepository
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val taskRepository = TaskRepository()

    private val _tasks = MutableLiveData<TaskState>()
    val tasks: LiveData<TaskState> = _tasks

    fun getTaks() = viewModelScope.launch {
        val list = taskRepository.getList()
        list.let {
            _tasks.value = TaskState.SuccessGet(it)
        }
    }

    fun update(task: Task) = viewModelScope.launch {
        val update = taskRepository.update(task)
        _tasks.value = TaskState.SuccessUpdate()
        Log.d("Update", "result: ${update}")
    }

    fun insert(task: Task) = viewModelScope.launch {
        taskRepository.insert(task)
        _tasks.value = TaskState.SuccessInsert()
    }

    fun delete(task: Task) = viewModelScope.launch {
        taskRepository.delete(task)
        _tasks.value = TaskState.SuccessDelete()
    }

}

sealed interface TaskState {
    class SuccessGet(val tasks: List<Task>?) : TaskState
    class SuccessInsert() : TaskState
    class SuccessDelete() : TaskState
    class SuccessUpdate() : TaskState
}
