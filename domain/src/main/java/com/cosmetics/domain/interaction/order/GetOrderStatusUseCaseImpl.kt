package com.cosmetics.domain.interaction.order

import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.order.OrderStatusResponse
import com.cosmetics.domain.repository.home.UserProfileRepository

class GetOrderStatusUseCaseImpl(private val profileRepository: UserProfileRepository) :
    GetOrderStatusUseCase {
    override suspend fun invoke(emptyString: String): Results<OrderStatusResponse> =
        profileRepository.getOrderStatuses()
}