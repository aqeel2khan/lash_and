package com.cosmetics.domain.interaction.order

import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.order.OrderStatusResponse

interface GetOrderStatusUseCase : BaseUseCase<String, OrderStatusResponse> {
    override suspend operator fun invoke(emptyString: String): Results<OrderStatusResponse>
}