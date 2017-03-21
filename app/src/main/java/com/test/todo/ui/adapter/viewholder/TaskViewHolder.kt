package com.test.todo.ui.adapter.viewholder

import android.view.View
import com.test.todo.model.Task
import com.test.todo.tools.interfaces.ButtonTaskListener
import com.test.todo.tools.interfaces.TaskClickListener
import com.test.todo.ui.widget.TaskImageButton
import kotlinx.android.synthetic.main.adapter_task.view.*

/**
 * Created by Benoit on 17/03/2017.
 */

class TaskViewHolder(val view: View, val onClickTaskAdapter: TaskClickListener?) : ButtonTaskListener {

    var task: Task? = null

    fun bindTask(task: Task) {
        this.task = task
        when (task.status) {
            Task.Status.OPENED.ordinal -> {
                view.taskButton.status = TaskImageButton.Status.OPEN
            }
            Task.Status.CLOSED.ordinal -> {
                view.taskButton.status = TaskImageButton.Status.CLOSE
            }
        }
        view.title.text = task.title

        view.taskButton.buttonListener = this
        view.setOnLongClickListener {
            view.taskButton.setDeleteOn(true)
            true
        }

        view.setOnClickListener {
            if (view.taskButton.status == TaskImageButton.Status.DELETE) {
                view.taskButton.setDeleteOff(true)
            } else {
                onClickTaskAdapter?.onTaskClick(task)
            }
        }
    }

    override fun onButtonTaskClick(status: TaskImageButton.Status) {
        onClickTaskAdapter?.onTaskClickWithStatus(task, status)
    }
}