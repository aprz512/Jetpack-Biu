package com.aprz.jetpack_biu.common

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

fun Toolbar.setFinishActivityListener(targetActivity: AppCompatActivity) {
    setFinishActivityListener(targetActivity, null)
}

fun Toolbar.setFinishActivityListener(targetActivity: AppCompatActivity, extraWork: Runnable?) {
    setOnClickListener {
        targetActivity.finish()
        extraWork?.run()
    }
}