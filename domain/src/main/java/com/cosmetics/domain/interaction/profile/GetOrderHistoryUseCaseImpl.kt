package com.cosmetics.domain.interaction.profile

import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.order.OrderHistoryResponse
import com.cosmetics.domain.repository.home.UserProfileRepository

class GetOrderHistoryUseCaseImpl(private val profileRepository: UserProfileRepository) :
    GetOrderHistoryUseCase {
    override suspend fun invoke(pageNumber: Int): Results<OrderHistoryResponse> =
        profileRepository.getOrderHistory(pageNumber)
}