package com.cosmetics.domain.interaction.profile.address

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.profile.AddressResponse
import com.cosmetics.domain.model.home.profile.SetCartAddressRequest

interface GetAddressUseCase : BaseUseCase<String, AddressResponse> {

    override suspend operator fun invoke(emptyString: String)
            : Results<AddressResponse>

}

interface GetDeliveryAddressUseCase : BaseUseCase<Boolean, AddressResponse> {

    override suspend operator fun invoke(isDeliverAddressSelection: Boolean)
            : Results<AddressResponse>

}

interface SetDeliveryAddressUseCase : BaseUseCase<SetCartAddressRequest, BaseResponse> {

    override suspend operator fun invoke(setCartAddressRequest: SetCartAddressRequest)
            : Results<BaseResponse>

}