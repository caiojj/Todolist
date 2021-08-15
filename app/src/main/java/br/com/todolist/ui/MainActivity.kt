package br.com.todolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.todolist.R
import br.com.todolist.databinding.ActivityMainBinding
import br.com.todolist.datasource.TaskDataSource

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTasks.adapter = adapter

        insertListeners()
    }

    private fun insertListeners() {
        binding.btnAdd.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
        }

        adapter.listenerDelet = {
            TaskDataSource.deleteTask(it);
            updateList()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) {
            binding.rvTasks.adapter = adapter
            adapter.submitList(TaskDataSource.getList())
            updateList()
        }
    }

    private fun updateList() {
        val list = TaskDataSource.getList()
        adapter.submitList(list)
        binding.includEmpty.layoutEmpty.visibility = if(list.isEmpty()) View.VISIBLE else View.GONE
    }

    companion object {
        private const val CREATE_NEW_TASK = 100
    }
}
