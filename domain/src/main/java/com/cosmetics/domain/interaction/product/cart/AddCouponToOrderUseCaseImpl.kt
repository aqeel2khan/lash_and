package com.cosmetics.domain.interaction.product.cart

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.cart.AddCouponRequest
import com.cosmetics.domain.repository.CartRepository

class AddCouponToOrderUseCaseImpl(private val cartRepository: CartRepository) :
    AddCouponToOrderUseCase {
    override suspend fun invoke(addCouponRequest: AddCouponRequest): Results<BaseResponse> =
        cartRepository.addCouponToOrder(addCouponRequest)
}