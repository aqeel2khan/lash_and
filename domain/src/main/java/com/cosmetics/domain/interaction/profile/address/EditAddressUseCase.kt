package com.cosmetics.domain.interaction.profile.address

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.address.CountryListResponse
import com.cosmetics.domain.model.home.address.ZoneListResponse
import com.cosmetics.domain.model.home.profile.UpdateAddressRequest

interface EditAddressUseCase : BaseUseCase<UpdateAddressRequest, BaseResponse> {

    override suspend operator fun invoke(updateAddressRequest: UpdateAddressRequest)
            : Results<BaseResponse>

}

interface GetCountryUseCase : BaseUseCase<String, CountryListResponse> {

    override suspend operator fun invoke(emptString: String)
            : Results<CountryListResponse>

}

interface GetZoneIdUseCase : BaseUseCase<String, ZoneListResponse> {

    override suspend operator fun invoke(id: String)
            : Results<ZoneListResponse>

}