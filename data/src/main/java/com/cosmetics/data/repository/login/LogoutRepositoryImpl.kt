package com.cosmetics.data.repository.login

import com.cosmetics.data.networking.service.LoginApi
import com.cosmetics.data.utils.safeApiCall
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.repository.login.LogoutRepository

class LogoutRepositoryImpl(private val loginApi: LoginApi) :
    LogoutRepository {
    override suspend fun handleLogout(): Results<BaseResponse> =
        safeApiCall {
            loginApi.handleLogout()
        }
}