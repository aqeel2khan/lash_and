package com.cosmetics.domain.interaction.product.cart

import com.cosmetics.domain.model.product.cart.AddProductToCartRequest
import com.cosmetics.domain.repository.ProductRepository

class AddProductToCartUseCaseImpl(private val productRepository: ProductRepository) :
    AddProductToCartUseCase {

    override suspend fun invoke(addProductToCartRequest: AddProductToCartRequest) =
        productRepository.addProductToCart(addProductToCartRequest)

}