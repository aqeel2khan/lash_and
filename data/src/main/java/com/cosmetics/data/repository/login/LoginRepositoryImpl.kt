package com.cosmetics.data.repository.login

import com.cosmetics.data.networking.service.LoginApi
import com.cosmetics.data.utils.safeApiCall
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.login.userlogin.LoginRequest
import com.cosmetics.domain.model.login.userlogin.LoginResponse
import com.cosmetics.domain.repository.login.LoginRepository

class LoginRepositoryImpl(private val loginApi: LoginApi) :
    LoginRepository {

    override suspend fun handleLogin(loginRequest: LoginRequest): Results<LoginResponse> {
        return safeApiCall {
            loginApi.login(loginRequest)
        }
    }
}