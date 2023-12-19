package com.cosmetics.domain.interaction.profile

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.profile.UpdateUserProfileRequest
import com.cosmetics.domain.repository.home.UserProfileRepository

class UpdateUserProfileUseCaseImpl(private val userProfileRepository: UserProfileRepository) :
    UpdateUserProfileUseCase {
    override suspend fun invoke(updateUserProfileRequest: UpdateUserProfileRequest)
            : Results<BaseResponse> =
        userProfileRepository.submit(updateUserProfileRequest)

}