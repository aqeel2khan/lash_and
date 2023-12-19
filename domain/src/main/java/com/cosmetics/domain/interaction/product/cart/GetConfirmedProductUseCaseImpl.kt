package com.cosmetics.domain.interaction.product.cart

import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.cart.ConfirmedProductsResponse
import com.cosmetics.domain.repository.ProductRepository

class GetConfirmedProductUseCaseImpl(private val productRepository: ProductRepository) :
    GetConfirmedProductUseCase {
    override suspend fun invoke(emptyString: String): Results<ConfirmedProductsResponse> =
        productRepository.getConfirmedProduct()
}