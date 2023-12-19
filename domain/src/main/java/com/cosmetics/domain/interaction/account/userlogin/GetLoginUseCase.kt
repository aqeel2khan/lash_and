package com.cosmetics.domain.interaction.account.userlogin

import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.login.userlogin.LoginRequest
import com.cosmetics.domain.model.login.userlogin.LoginResponse

interface GetLoginUseCase : BaseUseCase<LoginRequest, LoginResponse> {

    override suspend operator fun invoke(location: LoginRequest): Results<LoginResponse>
}