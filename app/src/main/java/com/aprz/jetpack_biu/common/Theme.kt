package com.aprz.jetpack_biu.common


import android.content.Context
import android.util.TypedValue
import com.aprz.jetpack_biu.R


fun getColorPrimary(context: Context): Int {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
    return typedValue.data
}