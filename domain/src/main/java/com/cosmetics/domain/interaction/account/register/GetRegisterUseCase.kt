package com.cosmetics.domain.interaction.account.register

import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.login.register.request.RegistrationRequest
import com.cosmetics.domain.model.login.register.response.RegistrationResponse

interface GetRegisterUseCase : BaseUseCase<RegistrationRequest, RegistrationResponse> {

    override suspend operator fun invoke(registrationRequest: RegistrationRequest):
            Results<RegistrationResponse>

}