package com.hadi.resources

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes

interface ResProvider {
    fun getStringRes(@StringRes resId: Int): String

    fun getStringRes(@StringRes resId: Int, vararg args: Any): String

    @ColorInt
    fun getColor(@ColorRes resId: Int): Int
}
