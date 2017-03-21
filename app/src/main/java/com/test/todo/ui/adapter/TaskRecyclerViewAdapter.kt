package com.test.todo.ui.adapter

import android.graphics.PorterDuff
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.test.todo.R
import com.test.todo.model.Task
import com.test.todo.model.dao.TaskDao
import io.realm.Sort
import kotlinx.android.synthetic.main.adapter_task.view.*

/**
 * Created by Benoit on 28/02/2017.
 */

class TaskRecyclerViewAdapter : RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TaskViewHolder {
        val view = View.inflate(parent?.context, R.layout.adapter_task, null)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder?, position: Int) {
        val task = getTaskAt(position)?.let { it } ?: return
        holder?.bindTask(task)
    }

    override fun getItemCount(): Int {
        return TaskDao.QUERY.count().toInt()
    }

    fun getTaskAt(position: Int): Task?{
        if (itemCount > position && position >= 0) {
            return TaskDao.QUERY.findAllSorted(TaskDao.ID, Sort.ASCENDING)[position]
        }
        return null
    }


    class TaskViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var task: Task? = null

        fun bindTask(task: Task) {
            this.task = task
            when (Task.Status.valueOf(task.status.toString())) {
                Task.Status.OPENED -> {
                    view.taskButton.setImageResource(R.drawable.ic_check_circle)
                    view.taskButton.setColorFilter(R.color.grey,PorterDuff.Mode.MULTIPLY)
                }
                Task.Status.CLOSED -> {
                    view.taskButton.setImageResource(R.drawable.ic_check_circle_outline)
                    view.taskButton.setColorFilter(R.color.green,PorterDuff.Mode.MULTIPLY)
                }

            }

            view.title.text = task.title
        }
    }

}
