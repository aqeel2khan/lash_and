package com.cosmetics.domain.interaction.product.cart

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.cart.UpdateCartRequest
import com.cosmetics.domain.repository.ProductRepository

class UpdateCartUseCaseImpl(private val productRepository: ProductRepository) : UpdateCartUseCase {
    override suspend fun invoke(updateCartRequest: UpdateCartRequest): Results<BaseResponse> =
        productRepository.updateCart(updateCartRequest)
}