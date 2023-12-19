package com.cosmetics.domain.interaction.profile

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.profile.UpdateUserProfileRequest

interface UpdateUserProfileUseCase : BaseUseCase<UpdateUserProfileRequest, BaseResponse> {

    override suspend operator fun invoke(updateUserProfileRequest: UpdateUserProfileRequest)
            : Results<BaseResponse>

}

