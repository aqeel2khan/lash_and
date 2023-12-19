package com.cosmetics.domain.repository.forgotpassword

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.login.forgotpassword.ForgotPasswordRequest as ForgotPasswordRequest1

interface ForgotPasswordRepository {
    suspend fun handleForgotPassword(forgotPasswordRequest: ForgotPasswordRequest1)
            : Results<BaseResponse>
}