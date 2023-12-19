package com.cosmetics.domain.interaction.profile.address

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.address.CountryListResponse
import com.cosmetics.domain.model.home.address.ZoneListResponse
import com.cosmetics.domain.model.home.profile.UpdateAddressRequest
import com.cosmetics.domain.repository.home.UserProfileRepository

class EditAddressUseCaseImpl(
    private val repository: UserProfileRepository
) : EditAddressUseCase {
    override suspend fun invoke(updateAddressRequest: UpdateAddressRequest): Results<BaseResponse> =
        repository.editAddress(updateAddressRequest)
}

class GetCountryUseCaseImpl(
    private val repository: UserProfileRepository
) : GetCountryUseCase {
    override suspend fun invoke(emptString: String): Results<CountryListResponse> =
        repository.getCountryList()
}

class GetZoneIdUseCaseImpl(
    private val repository: UserProfileRepository
) : GetZoneIdUseCase {
    override suspend fun invoke(id: String): Results<ZoneListResponse> =
        repository.getZoneList(id)
}