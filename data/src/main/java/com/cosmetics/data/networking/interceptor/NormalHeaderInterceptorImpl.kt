package com.cosmetics.data.networking.interceptor

import androidx.databinding.library.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.KoinComponent

interface NormalHeaderInterceptor : Interceptor
class NormalHeaderInterceptorImpl : NormalHeaderInterceptor, KoinComponent {
    override fun intercept(chain: Interceptor.Chain): Response {
        /*     var language = "en"
             if (!PreferenceDelegate.currentLanguage.isNullOrEmpty()) {
                 language = PreferenceDelegate.currentLanguage
             }
             val request = chain
                 .request()
                 .newBuilder()
                 .addHeader("version", BuildConfig.VERSION_CODE.toString())
                 .addHeader("language", language)
                 .addHeader("env", getEnv())
                 .addHeader("device", "Android")
                 .build()*/

        return chain.proceed(chain.request())
    }

    private fun getEnv(): String =
        if (BuildConfig.DEBUG) {
            "local"
        } else {
            "production"
        }
}