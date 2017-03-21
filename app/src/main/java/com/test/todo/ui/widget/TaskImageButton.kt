package com.test.todo.ui.widget

import android.annotation.TargetApi
import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageButton
import com.test.todo.R
import com.test.todo.tools.interfaces.ButtonTaskListener

/**
 * Created by Benoit on 16/03/2017.
 */

class TaskImageButton : ImageButton {

    var status = Status.OPEN
        set(value) {
            field = value
            setBackgroundFromStatus(this.status)
        }

    var previousStatus: Status? = null

    var buttonListener : ButtonTaskListener? = null

    enum class Status {
        CLOSE, DELETE, OPEN
    }

    constructor(context: Context) :
            super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) :
            super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    fun init() {
        setBackgroundResource(android.R.color.transparent)
//        setBackgroundFromStatus(status)
        setOnClickListener {
            buttonListener?.onButtonTaskClick(status)
        }
    }

    fun setDeleteOn(anim: Boolean) {
        setDelete(anim, true)
    }

    fun setDeleteOff(anim: Boolean) {
        setDelete(anim, false)
    }

    private fun setDelete(anim: Boolean, isOn: Boolean) {
        if (isOn) {
            previousStatus = status
            status = Status.DELETE
        } else {
            status = previousStatus?: Status.OPEN
        }
//        if (anim) {
//
//        } else {
//
//        }
    }

    private fun setBackgroundFromStatus(status: Status) {
        when (status) {
            Status.CLOSE -> {
                setImageResource(R.drawable.ic_check_circle)
                setColorFilter(resources.getColor(R.color.green), PorterDuff.Mode.MULTIPLY)
            }
            Status.DELETE -> {
                setImageResource(R.drawable.ic_delete)
                setColorFilter(resources.getColor(R.color.red), PorterDuff.Mode.MULTIPLY)
            }
            Status.OPEN -> {
                setImageResource(R.drawable.ic_check_circle_outline)
                setColorFilter(resources.getColor(R.color.grey), PorterDuff.Mode.MULTIPLY)
            }
        }
    }


}