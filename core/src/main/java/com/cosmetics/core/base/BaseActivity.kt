package com.cosmetics.core.base

import android.os.Bundle
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.cosmetics.core.commonviews.dialogs.LoadingDialog
import com.cosmetics.data.networking.interceptor.getLanguage
import com.cosmetics.domain.di.PreferenceDelegate
import java.util.*

open class BaseActivity : LocalizationActivity() {

    private lateinit var loader: LoadingDialog
    fun changeToEnglishLanguage() {
        PreferenceDelegate.currentLanguage = "en"
        setLanguage(Locale.ENGLISH)
    }

    fun resetToDefaultLanguage() {
        if (getLanguage() == "ar") changeToEnglishLanguage()
    }

    fun changeToArabicLanguage() {
        PreferenceDelegate.currentLanguage = "ar"
        setLanguage("ar")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loader = LoadingDialog(this)
    }

    /**
     * Dismiss dialog
     */
    fun dismissProgress() {
        try {
            loader.cancel()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * Show dialog
     */
    fun showProgress() {
        try {
            loader.toggle()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}