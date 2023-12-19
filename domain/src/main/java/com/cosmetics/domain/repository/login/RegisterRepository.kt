package com.cosmetics.domain.repository.login

import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.login.register.request.RegistrationRequest
import com.cosmetics.domain.model.login.register.response.RegistrationResponse

interface RegisterRepository {
    suspend fun handleRegister(registrationRequest: RegistrationRequest): Results<RegistrationResponse>
}