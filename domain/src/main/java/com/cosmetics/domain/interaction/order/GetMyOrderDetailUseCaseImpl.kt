package com.cosmetics.domain.interaction.order

import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.order.MyOrderDetailsResponse
import com.cosmetics.domain.repository.home.UserProfileRepository

class GetMyOrderDetailUseCaseImpl(private val profileRepository: UserProfileRepository) :
    GetMyOrderDetailUseCase {
    override suspend fun invoke(id: String): Results<MyOrderDetailsResponse> =
        profileRepository.getOrderDetails(id)
}