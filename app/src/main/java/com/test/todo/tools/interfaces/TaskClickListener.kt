package com.test.todo.tools.interfaces

import com.test.todo.model.Task
import com.test.todo.ui.widget.TaskImageButton

/**
 * Created by Benoit on 06/03/2017.
 */

interface TaskClickListener {
    fun onTaskClickWithStatus(task: Task?, status: TaskImageButton.Status)
    fun onTaskClick(task: Task?)
}