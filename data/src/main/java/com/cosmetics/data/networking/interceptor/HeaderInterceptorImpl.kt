package com.cosmetics.data.networking.interceptor

import com.cosmetics.domain.di.PreferenceDelegate
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.KoinComponent

interface HeaderInterceptor : Interceptor
class HeaderInterceptorImpl : HeaderInterceptor, KoinComponent {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${PreferenceDelegate.token}")
            .addHeader("X-Oc-Merchant-Language", getNetworkLanguage())
            .build()
        return chain.proceed(request)
    }
}

fun getNetworkLanguage() =
    if (getLanguage() == "en") "en-gb"
    else "ar"


fun getLanguage() =
    if (PreferenceDelegate.currentLanguage.isNullOrEmpty() ||
        PreferenceDelegate.currentLanguage.isBlank()
    ) {
        "en"
    } else {
        PreferenceDelegate.currentLanguage
    }