package com.cosmetics.data.networking

import com.cosmetics.data.BuildConfig
import com.cosmetics.data.networking.interceptor.*
import com.cosmetics.data.utils.CleanGsonConverter
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object LushApiClient : KoinComponent {
    private val connectivityInterceptor: ConnectivityInterceptor by inject()
    private val headerInterceptor: HeaderInterceptor by inject()
    private val authenticator: Authenticator by inject()
    private val normalHeaderInterceptor: NormalHeaderInterceptor by inject()
    private val authorizationInterceptor: AuthorizationInterceptorImpl by inject()
    private val getTokenHeaderInterceptorImpl: GetTokenHeaderInterceptorImpl by inject()

    private val httpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    fun getApiClient(okHttpClient: OkHttpClient, url: String): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(url)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(CleanGsonConverter.create())
        //.addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getOuthTokenOkhttpClient(): OkHttpClient {
        var builder = OkHttpClient().newBuilder()
        builder.addInterceptor(connectivityInterceptor)
        builder.addInterceptor(getTokenHeaderInterceptorImpl)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }

    fun getUserOkhttpClient(): OkHttpClient {
        var builder = OkHttpClient().newBuilder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
        builder.addInterceptor(connectivityInterceptor)
        builder.addInterceptor(headerInterceptor)
        builder.authenticator(authenticator)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }

    inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
        val retrofit = getApiClient(
            okHttpClient,
            url
        )
        return retrofit.create(T::class.java)
    }

}