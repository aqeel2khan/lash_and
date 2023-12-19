package com.cosmetics.domain.interaction.account.forgotpassword

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.login.forgotpassword.ForgotPasswordRequest

interface GetForgotPasswordUseCase : BaseUseCase<ForgotPasswordRequest, BaseResponse> {

    override suspend operator fun invoke(forgotPasswordRequest: ForgotPasswordRequest): Results<BaseResponse>

}