package com.cosmetics.domain.interaction.product.cart

import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.cart.ConfirmOrderResponse
import com.cosmetics.domain.repository.ProductRepository

class ConfirmedOrderUseCaseImpl(
    private val productRepository: ProductRepository
) : ConfirmedOrderUseCase {
    override suspend fun invoke(emptyString: String): Results<ConfirmOrderResponse> =
        productRepository.confirmedProduct()
}