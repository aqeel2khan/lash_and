package com.cosmetics.domain.interaction.order

import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.order.MyOrderDetailsResponse

interface GetMyOrderDetailUseCase : BaseUseCase<String, MyOrderDetailsResponse> {
    override suspend operator fun invoke(id: String): Results<MyOrderDetailsResponse>
}