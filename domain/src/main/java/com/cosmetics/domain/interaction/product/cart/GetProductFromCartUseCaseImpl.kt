package com.cosmetics.domain.interaction.product.cart

import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.cart.ProductInCartResponse
import com.cosmetics.domain.repository.ProductRepository

class GetProductFromCartUseCaseImpl(
    private val productRepository: ProductRepository
) : GetProductFromCartUseCase {
    override suspend fun invoke(emptyString: String): Results<ProductInCartResponse> {
        return productRepository.getProductsFromCart()
    }
}