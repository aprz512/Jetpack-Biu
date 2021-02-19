package com.aprz.jetpack_biu.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var viewBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = inflateViewBinding()
        setContentView(viewBinding.root)

        initView()
        bindEvent()
        sendHttpRequest()
    }

    abstract fun inflateViewBinding(): VB

    abstract fun initView()

    abstract fun bindEvent()

    abstract fun sendHttpRequest()

}