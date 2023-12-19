package com.cosmetics.domain.interaction.account.register

import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.login.register.request.RegistrationRequest
import com.cosmetics.domain.model.login.register.response.RegistrationResponse
import com.cosmetics.domain.repository.login.GetTokenRepository
import com.cosmetics.domain.repository.login.RegisterRepository

class GetRegisterUseCaseImpl(
    private val repository: RegisterRepository,
    private val getTokenRepository: GetTokenRepository
) : GetRegisterUseCase {

    override suspend operator fun invoke(request: RegistrationRequest):
            Results<RegistrationResponse> {
        var tokenResponse = getTokenRepository.fetchToken()
        return when (tokenResponse) {
            is Results.Success -> {
                PreferenceDelegate.token = tokenResponse.data.data.accessToken
                return repository.handleRegister(request)
            }
            is Results.Error -> {
                Results.Error(tokenResponse.message)
            }
            else -> {
                Results.Error("")
            }
        }
    }
}