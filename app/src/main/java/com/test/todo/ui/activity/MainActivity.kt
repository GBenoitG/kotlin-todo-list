package com.test.todo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.test.todo.R
import com.test.todo.model.Task
import com.test.todo.model.dao.TaskDao
import com.test.todo.tools.DBManager
import com.test.todo.tools.Extra
import com.test.todo.tools.interfaces.TaskClickListener
import com.test.todo.ui.adapter.TaskAdapter
import io.realm.Realm
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TaskClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.actionbar_all_task)

        val adapter = TaskAdapter(this, this)
        listView.adapter = adapter

        DBManager.instance.realm!!.addChangeListener { realm ->
            adapter.notifyDataSetChanged()
        }

        listView.setOnItemLongClickListener { parent, view, position, id ->
            TaskDao.deleteTask(TaskDao.getById(id.toInt())!!)
            true
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(Extra.EXTRA_ID, id)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.app_bar_add -> generateDataTask()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun generateDataTask() {
        var lastId: Int = if (TaskDao.getAll().size > 0) {
            TaskDao.QUERY.findAllSorted(TaskDao.ID, Sort.DESCENDING).first().id
        } else {
            0
        }
        lastId++

        val task: Task = Task()
        task.id = lastId
        task.title = "title $lastId"
        task.status = Task.Status.OPENED.ordinal

        TaskDao.insertTask(task)
    }

    override fun onTaskClicked(task: Task) {
        TaskDao.updateTask(Realm.Transaction { realm ->
            task.status = when (task.status) {
                Task.Status.OPENED.ordinal -> Task.Status.CLOSED.ordinal
                else -> Task.Status.OPENED.ordinal
            }
        })
    }
}
