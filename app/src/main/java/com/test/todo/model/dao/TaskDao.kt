package com.test.todo.model.dao

import com.test.todo.model.Task
import com.test.todo.tools.DBManager
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import java.util.*

/**
 * Created by Benoit on 07/03/2017.
 */

open class TaskDao {

    companion object {

        const val ID = "id"
        val QUERY: RealmQuery<Task>
            get() {
                return DBManager.instance.realm!!.where(Task::class.java)
            }

        fun getById(id: Int): Task? {
            return QUERY.equalTo(ID,id).findFirst()
        }

        fun getAll(): RealmResults<Task> {
            return QUERY.findAll()
        }

        fun insertTask(task: Task): Task? {
            return DBManager.instance.insert(task) as Task?
        }

        fun insertTasks(tasks: ArrayList<Task>){
            DBManager.instance.insert(tasks)
        }

        fun updateTask(transaction: Realm.Transaction) {
            DBManager.instance.updateObject(transaction)
        }

        fun deleteTask(task: Task) {
            DBManager.instance.deleteObject(task)
        }

    }
}