package com.test.todo

import android.app.Application
import com.test.todo.tools.DBManager

/**
 * Created by Benoit on 28/02/2017.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DBManager.createInstance(this)
    }
}