package com.cosmetics.data.networking.interceptor

import android.content.Context
import com.cosmetics.data.R
import com.cosmetics.domain.di.getKoinInstance
import okhttp3.*
import java.io.IOException

const val CONSTANT_TOKEN = "bHVzaF8xMjNfYXBpX2NsaWVudF9pZDpsdXNoXzEyM19hcGlfc2VjcmV0X2lk"
const val BASIC = "Basic"
const val AUTHORIZATION = "Authorization"

class AuthorizationInterceptorImpl : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401) {
            throw AuthenticatorException()
        }
        return null
    }
}

class GetTokenHeaderInterceptorImpl : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .addHeader(AUTHORIZATION, "$BASIC $CONSTANT_TOKEN")
            .build()
        return chain.proceed(request)
    }
}

open class AuthenticatorException : IOException() {
    private val context: Context = getKoinInstance()
    override val message: String
        get() = context.getString(R.string.something_went_wrong)
}