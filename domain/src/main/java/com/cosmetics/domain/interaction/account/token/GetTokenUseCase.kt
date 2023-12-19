package com.cosmetics.domain.interaction.account.token

import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.login.token.GetTokenResponse

interface GetTokenUseCase : BaseUseCase<String, GetTokenResponse> {

    override suspend operator fun invoke(param: String):
            Results<GetTokenResponse>

}