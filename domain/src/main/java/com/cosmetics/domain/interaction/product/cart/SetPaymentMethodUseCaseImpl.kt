package com.cosmetics.domain.interaction.product.cart

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.cart.SetPaymentMethodRequest
import com.cosmetics.domain.repository.ProductRepository

class SetPaymentMethodUseCaseImpl(private val productRepository: ProductRepository) :
    SetPaymentMethodUseCase {
    override suspend fun invoke(setPaymentMethodRequest: SetPaymentMethodRequest): Results<BaseResponse> =
        productRepository.setPaymentMethod(setPaymentMethodRequest)
}