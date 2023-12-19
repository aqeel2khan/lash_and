package com.cosmetics.domain.interaction.product.cart

import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.profile.CartCountResponse
import com.cosmetics.domain.repository.ProductRepository

class GetCartCountUseCaseImpl(private val productRepository: ProductRepository) :
    GetCartCountUseCase {
    override suspend fun invoke(emptyString: String): Results<CartCountResponse> =
        productRepository.getCartCount()
}