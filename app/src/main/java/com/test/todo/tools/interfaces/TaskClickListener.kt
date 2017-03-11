package com.test.todo.tools.interfaces

import com.test.todo.model.Task

/**
 * Created by Benoit on 06/03/2017.
 */

interface TaskClickListener {
    fun onTaskClicked(task: Task)
}