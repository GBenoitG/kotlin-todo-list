package com.test.todo.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.test.todo.R
import com.test.todo.model.Task
import com.test.todo.model.dao.TaskDao
import com.test.todo.tools.Extra
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), View.OnFocusChangeListener {

    var task: Task? = null
    var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        val id = intent.getLongExtra(Extra.EXTRA_ID, -1).toInt()
        val id = intent.getIntExtra(Extra.EXTRA_ID, -1)

        task = TaskDao.getById(id)
        if (task != null) {
            configureView()
        } else {
            contentLayout.visibility = View.GONE
            noDataMsg.visibility = View.VISIBLE
        }
    }

    private fun configureView() {
        detailTitle.text = task?.title ?: ""
        detailTitleEdit.setText(task?.title ?: "", TextView.BufferType.EDITABLE)
        detailDescription.text = task?.description ?: ""
        detailDescriptionEdit.setText(task?.description ?: "", TextView.BufferType.EDITABLE)

        detailTitle.setOnClickListener {
            switcherTitle.showNext()
            detailTitleEdit.requestFocus()
        }

        detailDescription.setOnClickListener {
            switcherDesc.showNext()
            detailDescriptionEdit.requestFocus()
        }

        detailTitleEdit.onFocusChangeListener = this
        detailDescriptionEdit.onFocusChangeListener = this
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_task, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.app_bar_edit -> if (item.title == "Save") {
                saveTask()
            } else {
                showEditUI()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveTask() {
        saveTitle()
        saveDesc()
        displayEditButton()
    }

    private fun saveDesc() {
        if (switcherDesc.nextView.id == R.id.detailDescription) {
            detailDescription.text = detailDescriptionEdit.text.toString()
            switcherDesc.showNext()
        }
        TaskDao.updateTask(Realm.Transaction { realm ->
            task?.description = detailDescription.text.toString()
            realm.copyToRealmOrUpdate(task)
        })
    }

    private fun saveTitle() {
        if (switcherTitle.nextView.id == R.id.detailTitle) {
            detailTitle.text = detailTitleEdit.text.toString()
            switcherTitle.showNext()
        }
        TaskDao.updateTask(Realm.Transaction { realm ->
            task?.title = detailTitle.text.toString()
            realm.copyToRealmOrUpdate(task)
        })
    }



    private fun showEditUI() {
        switcherTitle.showNext()
        switcherDesc.showNext()
        displaySaveButton()
    }

    private fun displaySaveButton() {
        val item = menu?.findItem(R.id.app_bar_edit)
        item?.icon = resources.getDrawable(R.drawable.ic_save)
        item?.title = "Save"
    }

    private fun displayEditButton() {
        val item = menu?.findItem(R.id.app_bar_edit)
        item?.icon = resources.getDrawable(R.drawable.ic_pencil)
        item?.title = "Edit"
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        when (v?.id) {
            R.id.detailTitleEdit -> if (hasFocus) displaySaveButton() else saveTitle()
            R.id.detailDescriptionEdit -> if (hasFocus) displaySaveButton() else saveDesc()
        }
    }
}
