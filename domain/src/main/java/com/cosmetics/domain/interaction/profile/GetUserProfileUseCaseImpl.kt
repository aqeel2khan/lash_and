package com.cosmetics.domain.interaction.profile

import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.profile.UserProfileResponse
import com.cosmetics.domain.repository.home.UserProfileRepository

class GetUserProfileUseCaseImpl(private val userProfileRepository: UserProfileRepository) :
    GetUserProfileUseCase {
    override suspend fun invoke(emptyString: String): Results<UserProfileResponse> =
        userProfileRepository.getUserProfile()
}