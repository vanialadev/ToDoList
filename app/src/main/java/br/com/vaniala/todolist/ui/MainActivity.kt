package br.com.vaniala.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import br.com.vaniala.todolist.databinding.ActivityMainBinding
import br.com.vaniala.todolist.datasource.TaskDataSource
import br.com.vaniala.todolist.ui.AddTaskActivity.Companion.TASK_ID

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTasks.adapter = adapter
        updateList()

        insertListeners()
    }

    private fun insertListeners() {

        binding.btnFab.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
        }

        adapter.listenerDelete = {
            TaskDataSource.deleteTask(it)
            updateList()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == RESULT_OK) updateList()
    }

        private fun updateList() {
            val list = TaskDataSource.getList()
            binding.includeEmpty.emptyState.visibility = if (list.isEmpty()) View.VISIBLE
            else View.GONE

            adapter.submitList(list)
        }

        companion object {
            private const val CREATE_NEW_TASK = 1000
        }
    }