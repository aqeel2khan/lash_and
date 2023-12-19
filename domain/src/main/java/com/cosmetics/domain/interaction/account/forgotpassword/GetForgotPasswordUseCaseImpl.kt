package com.cosmetics.domain.interaction.account.forgotpassword

import com.cosmetics.domain.model.login.forgotpassword.ForgotPasswordRequest
import com.cosmetics.domain.repository.forgotpassword.ForgotPasswordRepository

class GetForgotPasswordUseCaseImpl(private val forgotPasswordRepository: ForgotPasswordRepository) :
    GetForgotPasswordUseCase {
    override suspend fun invoke(forgotPasswordRequest: ForgotPasswordRequest) =
        forgotPasswordRepository.handleForgotPassword(forgotPasswordRequest)
}