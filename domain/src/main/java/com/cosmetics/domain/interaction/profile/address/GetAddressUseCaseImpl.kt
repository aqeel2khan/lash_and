package com.cosmetics.domain.interaction.profile.address

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.profile.AddressResponse
import com.cosmetics.domain.model.home.profile.SetCartAddressRequest
import com.cosmetics.domain.repository.home.UserProfileRepository

class GetAddressUseCaseImpl(private val profileRepository: UserProfileRepository) :
    GetAddressUseCase {
    override suspend fun invoke(emptyString: String): Results<AddressResponse> =
        profileRepository.getAddress()

}


class GetDeliveryAddressUseCaseImpl(private val profileRepository: UserProfileRepository) :
    GetDeliveryAddressUseCase {
    override suspend fun invoke(isDeliverAddressSelection: Boolean): Results<AddressResponse> =
        profileRepository.getDeliveryAddress(isDeliverAddressSelection)

}

class SetDeliveryAddressUseCaseImpl(private val profileRepository: UserProfileRepository) :
    SetDeliveryAddressUseCase {
    override suspend fun invoke(id: SetCartAddressRequest): Results<BaseResponse> =
        profileRepository.editDeliveryAddress(id)

}