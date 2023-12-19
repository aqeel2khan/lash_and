package com.cosmetics.domain.interaction.profile.address

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.delivermethods.DeliveryMethodRequest
import com.cosmetics.domain.model.home.delivermethods.DeliveryMethodResponse
import com.cosmetics.domain.repository.home.DeliveryMethodRepository

interface GetDeliveryMethodUseCase : BaseUseCase<String, DeliveryMethodResponse> {
    override suspend operator fun invoke(emptyString: String)
            : Results<DeliveryMethodResponse>
}

interface SetDeliveryMethodUseCase : BaseUseCase<DeliveryMethodRequest, BaseResponse> {
    override suspend operator fun invoke(id: DeliveryMethodRequest)
            : Results<BaseResponse>
}

class GetDeliveryMethodUseCaseImpl(
    private val deliveryMethodRepository: DeliveryMethodRepository
) : GetDeliveryMethodUseCase {
    override suspend fun invoke(emptyString: String): Results<DeliveryMethodResponse> =
        deliveryMethodRepository.getDeliveryMethod()

}

class SetDeliveryMethodUseCaseImpl(
    private val deliveryMethodRepository: DeliveryMethodRepository
) : SetDeliveryMethodUseCase {
    override suspend fun invoke(id: DeliveryMethodRequest): Results<BaseResponse> =
        deliveryMethodRepository.setDeliveryMethod(id)
}