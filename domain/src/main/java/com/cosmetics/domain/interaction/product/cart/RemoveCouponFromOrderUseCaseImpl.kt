package com.cosmetics.domain.interaction.product.cart

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.repository.CartRepository

class RemoveCouponFromOrderUseCaseImpl(private val cartRepository: CartRepository) :
    RemoveCouponFromOrderUseCase {
    override suspend fun invoke(emptyString: String): Results<BaseResponse> =
        cartRepository.removeCouponFromOrder()
}