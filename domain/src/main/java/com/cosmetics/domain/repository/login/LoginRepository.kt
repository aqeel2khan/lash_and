package com.cosmetics.domain.repository.login

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.login.token.GetTokenResponse
import com.cosmetics.domain.model.login.userlogin.LoginRequest
import com.cosmetics.domain.model.login.userlogin.LoginResponse

interface LoginRepository {
    suspend fun handleLogin(loginRequest: LoginRequest): Results<LoginResponse>
}

interface LogoutRepository {
    suspend fun handleLogout(): Results<BaseResponse>
}

interface GetTokenRepository {
    suspend fun fetchToken(): Results<GetTokenResponse>
}