package com.cosmetics.data.di

import android.content.Context
import com.cosmetics.data.networking.LushApiClient.createWebService
import com.cosmetics.data.networking.LushApiClient.getOuthTokenOkhttpClient
import com.cosmetics.data.networking.LushApiClient.getUserOkhttpClient
import com.cosmetics.data.networking.interceptor.*
import com.cosmetics.data.networking.service.*
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

//private const val BASE_URL = "http://lushkw.com/"
var BASE_URL = "http://3.131.156.224/"

//const val BASE_URL = "http://13.59.182.96/"
const val OAUTH = "OAUTH"
const val DEFAULT = "Default"
const val AUTHENTICATED = "Authenticated"
val networkModule = module {
    factory(named(OAUTH)) { getOuthTokenOkhttpClient() }
    factory(named(AUTHENTICATED)) { provideAuthOkHttpClient() }
    factory { provideHeaderInterceptor() }
    factory { provideNormalHeaderInterceptor() }
    factory { provideConnectionInterceptor(androidContext()) }
    factory { provideAuthorizationInterceptor() }
    factory { GetTokenHeaderInterceptorImpl() }
    single { createWebService<GetTokenService>(get(named(OAUTH)), BASE_URL) }
    single { createWebService<LoginApi>(get(named(AUTHENTICATED)), BASE_URL) }
    single { createWebService<ForgotPasswordService>(get(named(AUTHENTICATED)), BASE_URL) }
    single { createWebService<HomeService>(get(named(AUTHENTICATED)), BASE_URL) }
    single { createWebService<CartService>(get(named(AUTHENTICATED)), BASE_URL) }
    single { createWebService<DeliveryMethodService>(get(named(AUTHENTICATED)), BASE_URL) }
    single { createWebService<AppInformationService>(get(named(AUTHENTICATED)), BASE_URL) }
}

fun provideAuthOkHttpClient(): OkHttpClient = getUserOkhttpClient()

fun provideConnectionInterceptor(context: Context): ConnectivityInterceptor =
    ConnectivityInterceptorImpl(context)

fun provideAuthorizationInterceptor(): Authenticator = AuthorizationInterceptorImpl()

fun provideHeaderInterceptor(): HeaderInterceptor = HeaderInterceptorImpl()

fun provideNormalHeaderInterceptor(): NormalHeaderInterceptor = NormalHeaderInterceptorImpl()