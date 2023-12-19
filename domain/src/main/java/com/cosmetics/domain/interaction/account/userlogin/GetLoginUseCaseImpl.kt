package com.cosmetics.domain.interaction.account.userlogin

import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.login.userlogin.LoginRequest
import com.cosmetics.domain.model.login.userlogin.LoginResponse
import com.cosmetics.domain.repository.login.GetTokenRepository
import com.cosmetics.domain.repository.login.LoginRepository

class GetLoginUseCaseImpl(
    private val loginRepository: LoginRepository,
    private val getTokenRepository: GetTokenRepository
) : GetLoginUseCase {
    /*override suspend operator fun invoke(loginRequest: LoginRequest) =
            loginRepository.handleLogin(loginRequest)
    */
    override suspend operator fun invoke(loginRequest: LoginRequest):
            Results<LoginResponse> {
        var tokenResponse = getTokenRepository.fetchToken()
        return when (tokenResponse) {
            is Results.Success -> {
                PreferenceDelegate.token = tokenResponse.data.data.accessToken
                loginRepository.handleLogin(loginRequest)
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