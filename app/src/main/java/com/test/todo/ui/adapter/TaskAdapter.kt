package com.test.todo.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.test.todo.R
import com.test.todo.model.Task
import com.test.todo.model.dao.TaskDao
import com.test.todo.tools.interfaces.TaskClickListener
import com.test.todo.ui.adapter.viewholder.TaskViewHolder
import io.realm.Sort

/**
 * Created by Benoit on 06/03/2017.
 */

class TaskAdapter(context: Context, val onClickTaskAdapter: TaskClickListener?) : BaseAdapter() {

    val context = context

    override fun getItemId(position: Int): Long {
        val task = getItem(position)?.let { it } ?: return -1
        return task.id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val task = getItem(position)?.let { it } ?: return null
        val view: View?
        val holder: TaskViewHolder
        if (convertView == null) {
            view = View.inflate(context, R.layout.adapter_task, null)
            holder = TaskViewHolder(view, onClickTaskAdapter)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as TaskViewHolder
        }

        holder.bindTask(task)

        return view
    }

    override fun getCount(): Int {
        return TaskDao.QUERY.count().toInt()
    }

    override fun getItem(position: Int): Task? {
        if (count > position && position >= 0) {
            return TaskDao.QUERY.findAllSorted(TaskDao.ID, Sort.ASCENDING)[position]
        }
        return null
    }
}
