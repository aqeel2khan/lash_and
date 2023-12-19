package com.cosmetics.domain.interaction.product.cart

import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.ProductListResponse
import com.cosmetics.domain.repository.ProductRepository

class SearchProductUseCaseImpl(private val productRepository: ProductRepository) :
    SearchProductUseCase {
    override suspend fun invoke(productName: String): Results<ProductListResponse> =
        productRepository.searchProduct(productName)
}