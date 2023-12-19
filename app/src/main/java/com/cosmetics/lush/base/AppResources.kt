package com.cosmetics.lush.base

import android.content.Context

class AppResources(private val context: Context) : IAppResources {

    override fun getString(resId: Int): String {
        return context.getString(resId)
    }

    override fun getStringArray(resId: Int): Array<String> {
        return context.resources.getStringArray(resId)
    }

}

interface IAppResources {
    fun getString(resId: Int): String

    fun getStringArray(resId: Int): Array<String>
}