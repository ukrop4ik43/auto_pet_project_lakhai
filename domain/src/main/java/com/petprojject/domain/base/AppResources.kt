package com.petprojject.domain.base

interface AppResources {
    fun getBoolean(resourceId: Int): Boolean
    fun getColor(resourceId: Int): Int
    fun getString(resourceId: Int): String
    fun getText(resourceId: Int): CharSequence
    fun getString(resourceId: Int, vararg arguments: Any?): String
    fun getStringArray(resourceId: Int): Array<String>
    fun getQuantityString(resourceId: Int, quantity: Int, vararg arguments: Any?): String
}