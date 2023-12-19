package com.cosmetics.domain.interaction.account.logout

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.repository.login.LogoutRepository

class GetLogoutUseCaseImpl(private val logoutRepository: LogoutRepository) :
    GetLogoutUseCase {
    override suspend fun invoke(empString: String): Results<BaseResponse> =
        logoutRepository.handleLogout()
}