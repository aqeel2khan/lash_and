package com.cosmetics.domain.interaction.profile

import com.cosmetics.domain.model.home.guest.CreateGuestUserRequest
import com.cosmetics.domain.repository.home.UserProfileRepository

class CreateGuestUserUseCaseImpl(
    private val repository: UserProfileRepository
) : CreateGuestUserUseCase {
    override suspend fun invoke(createGuestUserRequest: CreateGuestUserRequest) =
        repository.createGuestUser(createGuestUserRequest)

}