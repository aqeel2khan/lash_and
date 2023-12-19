package com.cosmetics.data.repository.login.gettoken

import com.cosmetics.data.networking.service.GetTokenService
import com.cosmetics.data.utils.safeApiCall
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.login.token.GetTokenResponse
import com.cosmetics.domain.repository.login.GetTokenRepository

class GetTokenRepositoryImpl(private val getTokenService: GetTokenService) :
    GetTokenRepository {
    override suspend fun fetchToken(): Results<GetTokenResponse> {
        return safeApiCall {
            getTokenService.getToken()
        }
    }
}