package com.cosmetics.data.repository.login.register

import com.cosmetics.data.networking.service.LoginApi
import com.cosmetics.data.utils.safeApiCall
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.login.register.request.RegistrationRequest
import com.cosmetics.domain.model.login.register.response.RegistrationResponse
import com.cosmetics.domain.repository.login.RegisterRepository

class RegisterRepositoryImpl(private val loginApi: LoginApi) :
    RegisterRepository {

    override suspend fun handleRegister(registrationRequest: RegistrationRequest)
            : Results<RegistrationResponse> =
        safeApiCall {
            loginApi.handleRegister(registrationRequest)
        }
}