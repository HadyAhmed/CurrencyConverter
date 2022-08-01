package com.hadi.resourcesimpl

import android.content.Context
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.hadi.resources.ResProvider

class ResourcesProviderImpl(
    private val context: Context
) : ResProvider {

    private val resources = context.resources

    override fun getStringRes(@StringRes resId: Int): String =
        resources.getString(resId)

    override fun getStringRes(resId: Int, vararg args: Any): String {
        return resources.getString(resId, *args)
    }

    override fun getColor(resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }
}