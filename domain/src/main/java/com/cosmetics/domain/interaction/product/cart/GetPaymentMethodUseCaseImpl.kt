package com.cosmetics.domain.interaction.product.cart

import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.cart.PaymentMethodResponse
import com.cosmetics.domain.repository.ProductRepository

class GetPaymentMethodUseCaseImpl(private val productRepository: ProductRepository) :
    GetPaymentMethodUseCase {
    override suspend fun invoke(emptyString: String): Results<PaymentMethodResponse> =
        productRepository.getPaymentMethod()
}