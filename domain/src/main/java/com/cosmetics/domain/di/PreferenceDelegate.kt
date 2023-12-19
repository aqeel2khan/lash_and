package com.cosmetics.domain.di

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private const val TOKEN = "token"
private const val CUSTOMER_ID = "customer_id"
private const val FIRST_NAME = "first_name"
private const val LAST_NAME = "last_name"
private const val EMAIL = "email"
private const val TELEPHONE = "telephone"
private const val CART_COUNT_PRODUCTS = "cart_count_products"
private const val APP_INFORMATION = "app_information"
private const val WISH_LIST_TOTAL = "wish_list_total"
private const val RECENT_SEARCH_LIST = "recent_search_list"
private const val CURRENT_LANGUAGE = "current_language"
private const val IS_LANGUAGE_SELECTION_SCREEN_SHOWN = "IS_LANGUAGE_SELECTION_SCREEN_SHOWN"

abstract class PreferenceDelegate<T> : ReadWriteProperty<Any, T> {

    companion object {
        val sharedPreferences: SharedPreferences by lazy {
            PreferenceManager.getDefaultSharedPreferences(getKoinInstance())
        }
        var isLanguageSelectionScreenShown by BooleanPreferenceDelegate(
            IS_LANGUAGE_SELECTION_SCREEN_SHOWN
        )
        var token by StringPreferenceDelegate(
            TOKEN
        )
        var customerId by StringPreferenceDelegate(
            CUSTOMER_ID
        )
        var firstName by StringPreferenceDelegate(
            FIRST_NAME
        )
        var lastName by StringPreferenceDelegate(
            LAST_NAME
        )
        var email by StringPreferenceDelegate(
            EMAIL
        )
        var telephone by StringPreferenceDelegate(
            TELEPHONE
        )
        var cartCountProducts by IntPreferenceDelegate(
            CART_COUNT_PRODUCTS
        )
        var appInformations by StringPreferenceDelegate(
            APP_INFORMATION
        )
        var wishlistTotal by StringPreferenceDelegate(
            WISH_LIST_TOTAL
        )
        var recentSearchList by StringPreferenceDelegate(
            RECENT_SEARCH_LIST
        )
        var currentLanguage by StringPreferenceDelegate(
            CURRENT_LANGUAGE
        )

        fun clearPreference() {
            sharedPreferences.edit {
                clear()
                commit()
            }
        }
    }

}

class StringPreferenceDelegate(private val key: String, private val defaultValue: String = "") :
    PreferenceDelegate<String>() {
    override fun getValue(thisRef: Any, property: KProperty<*>) =
        sharedPreferences.getString(key, defaultValue)!!

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) =
        sharedPreferences.edit { putString(key, value) }
}

class IntPreferenceDelegate(private val key: String, private val defaultValue: Int = 0) :
    PreferenceDelegate<Int>() {
    override fun getValue(thisRef: Any, property: KProperty<*>) =
        sharedPreferences.getInt(key, defaultValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) =
        sharedPreferences.edit { putInt(key, value) }
}

class LongPreferenceDelegate(private val key: String, private val defaultValue: Long = 0.toLong()) :
    PreferenceDelegate<Long>() {
    override fun getValue(thisRef: Any, property: KProperty<*>) =
        sharedPreferences.getLong(key, defaultValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) =
        sharedPreferences.edit { putLong(key, value) }
}

class FloatPreferenceDelegate(
    private val key: String,
    private val defaultValue: Float = 0.toFloat()
) : PreferenceDelegate<Float>() {
    override fun getValue(thisRef: Any, property: KProperty<*>) =
        sharedPreferences.getFloat(key, defaultValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Float) =
        sharedPreferences.edit { putFloat(key, value) }
}

class BooleanPreferenceDelegate(
    private val key: String,
    private val defaultValue: Boolean = false
) : PreferenceDelegate<Boolean>() {
    override fun getValue(thisRef: Any, property: KProperty<*>) =
        sharedPreferences.getBoolean(key, defaultValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) =
        sharedPreferences.edit { putBoolean(key, value) }
}