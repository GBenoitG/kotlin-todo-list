package com.test.todo.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Benoit on 28/02/2017.
 */
open class Task : RealmObject() {

    @PrimaryKey
    var id: Int = 0
    var title = ""
    var description = ""
    var status: Int = Status.OPENED.ordinal

    enum class Status(val status: Int) {
        CLOSED(0), OPENED(1)
    }
}