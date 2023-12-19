package com.cosmetics.data.repository.login.forgotpassword

import com.cosmetics.data.networking.service.ForgotPasswordService
import com.cosmetics.data.utils.safeApiCall
import com.cosmetics.domain.model.login.forgotpassword.ForgotPasswordRequest
import com.cosmetics.domain.repository.forgotpassword.ForgotPasswordRepository

class ForgotPasswordRepositoryImpl(private val forgotPasswordService: ForgotPasswordService) :
    ForgotPasswordRepository {
    override suspend fun handleForgotPassword(forgotPasswordRequest: ForgotPasswordRequest) =
        safeApiCall {
            forgotPasswordService.handleForgotPassword(forgotPasswordRequest)
        }
}