package com.cosmetics.domain.interaction.product.cart

import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.cart.ConfirmOrderResponse
import com.cosmetics.domain.model.product.cart.ConfirmedProductsResponse

interface GetConfirmedProductUseCase : BaseUseCase<String, ConfirmedProductsResponse> {
    override suspend operator fun invoke(emptyString: String): Results<ConfirmedProductsResponse>
}

interface ConfirmedOrderUseCase : BaseUseCase<String, ConfirmOrderResponse> {
    override suspend operator fun invoke(emptyString: String): Results<ConfirmOrderResponse>
}