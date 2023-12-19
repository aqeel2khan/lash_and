package com.cosmetics.domain.interaction.account.token

import com.cosmetics.domain.repository.login.GetTokenRepository

class GetTokenUseCaseImpl(private val repository: GetTokenRepository) : GetTokenUseCase {

    override suspend operator fun invoke(request: String) =
        repository.fetchToken()
}