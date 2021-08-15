package br.com.todolist.ui
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.com.todolist.R
import br.com.todolist.databinding.ActivityMainBinding
import br.com.todolist.datasource.model.Task
import br.com.todolist.datasource.viewModel.MainViewModel
import br.com.todolist.datasource.viewModel.TaskState

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    companion object {
        val viewModel by lazy { MainViewModel() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTasks.adapter = adapter

        insertListeners()

        viewModel.getTaks()

        observer()
    }

    private fun observer() {
        viewModel.tasks.observe(this) { taskState ->
            when(taskState) {
                is TaskState.SuccessGet -> {
                    val list = taskState.tasks
                    adapter.submitList(list)
                    isEmptyTask(list)
                }
                is TaskState.SuccessDelete -> viewModel.getTaks()
                is TaskState.SuccessUpdate -> {
                    viewModel.getTaks()
                    Toast
                        .makeText(this, "Tarefa editada", Toast.LENGTH_SHORT)
                        .show()
                }
                is TaskState.SuccessInsert -> {
                    viewModel.getTaks()
                    Toast
                        .makeText(this, "Tarefa adicinada", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun insertListeners() {
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK, it)
            startActivity(intent)
        }

        adapter.listenerDelet = {
            viewModel.delete(it)
        }
    }

    private fun isEmptyTask(list: List<Task>?) {
        binding.includEmpty.layoutEmpty.visibility = if(list?.isEmpty() == true) View.VISIBLE else View.GONE
    }
}
