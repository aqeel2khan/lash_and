package com.cosmetics.domain.interaction.profile

import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.order.OrderHistoryResponse

interface GetOrderHistoryUseCase : BaseUseCase<Int, OrderHistoryResponse> {

    override suspend operator fun invoke(pageNumber: Int)
            : Results<OrderHistoryResponse>

}