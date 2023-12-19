package com.cosmetics.domain.interaction.profile

import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.profile.UserProfileResponse

interface GetUserProfileUseCase : BaseUseCase<String, UserProfileResponse> {

    override suspend operator fun invoke(emptyString: String)
            : Results<UserProfileResponse>

}