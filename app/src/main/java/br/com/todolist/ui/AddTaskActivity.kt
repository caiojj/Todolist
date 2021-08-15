package br.com.todolist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.todolist.R
import br.com.todolist.databinding.ActivityAddTaskBinding
import br.com.todolist.datasource.model.Task
import br.com.todolist.extensions.format
import br.com.todolist.extensions.text
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    companion object {
        const val TASK = "task"
    }
    private var uid: Int = 0
    private lateinit var binding: ActivityAddTaskBinding
    private var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra(TASK)) {
            val task = intent.getSerializableExtra(TASK) as Task
            task.let {
                binding.titTitle.text = it.title
                binding.tilDate.text = it.date
                binding.tilHour.text = it.hour
                binding.btnNewTask.text = getString(R.string.salvar)
                edit = true
                uid = it.uid
            }
        }
        insertListeners()
    }

    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePick = MaterialDatePicker.Builder.datePicker().build()
            datePick.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offset).format()
            }
            datePick.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tilHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker
                .Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timePicker.addOnPositiveButtonClickListener {
                binding.tilHour.text = "${timePicker.hour.toString()}:${timePicker.minute}"
            }

            timePicker.show(supportFragmentManager, null)
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnNewTask.setOnClickListener {
            val task = Task(
                uid = uid,
                title =  binding.titTitle.text,
                hour = binding.tilHour.text,
                date = binding.tilDate.text
            )

            if(edit) {
                MainActivity.viewModel.update(task)
            } else {
                MainActivity.viewModel.insert(task)
            }
            finish()
        }
    }
}