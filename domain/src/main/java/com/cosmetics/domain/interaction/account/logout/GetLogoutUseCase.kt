package com.cosmetics.domain.interaction.account.logout

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results

interface GetLogoutUseCase : BaseUseCase<String, BaseResponse> {

    override suspend operator fun invoke(empString: String): Results<BaseResponse>
}