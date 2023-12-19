package com.cosmetics.domain.interaction.profile

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.guest.CreateGuestUserRequest

interface CreateGuestUserUseCase : BaseUseCase<CreateGuestUserRequest, BaseResponse> {

    override suspend operator fun invoke(createGuestUserRequest: CreateGuestUserRequest)
            : Results<BaseResponse>

}