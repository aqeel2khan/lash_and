package com.cosmetics.lush.base

import android.app.Application
import com.cosmetics.data.di.BASE_URL
import com.cosmetics.data.di.networkModule
import com.cosmetics.data.di.repositoryModule
import com.cosmetics.domain.di.interactionModule
import com.cosmetics.lush.BuildConfig
import com.cosmetics.lush.di.commonModule
import com.cosmetics.lush.di.viewModelModule
import com.cosmetics.lush.ui.isUserLoggedIn
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        BASE_URL = BuildConfig.SERVER_URL
        startKoin {
            androidContext(this@LApplication)
            modules(
                listOf(
                    commonModule,
                    interactionModule,
                    networkModule,
                    viewModelModule,
                    repositoryModule
                )
            )
        }
        setUserId()
    }

    private fun setUserId() {
        if (isUserLoggedIn()) {
            //FirebaseCrashlytics.getInstance().setUserId(PreferenceDelegate.email)
        }
    }
}