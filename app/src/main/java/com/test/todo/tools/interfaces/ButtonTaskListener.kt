package com.test.todo.tools.interfaces

import com.test.todo.ui.widget.TaskImageButton

/**
 * Created by Benoit on 16/03/2017.
 */
interface ButtonTaskListener {
    fun onButtonTaskClick(status: TaskImageButton.Status)
}