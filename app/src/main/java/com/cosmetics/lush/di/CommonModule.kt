package com.cosmetics.lush.di

import android.content.res.Configuration
import com.cosmetics.core.utils.SharedPreferencesHelper
import com.cosmetics.data.coroutine.CoroutineContextProvider
import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.lush.base.AppResources
import com.cosmetics.lush.base.IAppResources
import com.cosmetics.lush.imageloading.ImageLoader
import com.cosmetics.lush.imageloading.ImageLoaderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.util.*

val commonModule = module {
    single { SharedPreferencesHelper(get()) }
    single { CoroutineContextProvider() }
    single<ImageLoader> { ImageLoaderImpl() }
    factory<IAppResources> {
        val original = androidContext()
        val config = Configuration().apply {
            setTo(original.resources.configuration)
            setLocale(Locale(PreferenceDelegate.currentLanguage))
        }
        return@factory AppResources(original.createConfigurationContext(config))
    }
}

