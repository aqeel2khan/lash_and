package com.cosmetics.domain.repository

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.cart.AddCouponRequest

interface CartRepository {
    suspend fun addCouponToOrder(addCouponRequest: AddCouponRequest): Results<BaseResponse>
    suspend fun removeCouponFromOrder(): Results<BaseResponse>
    suspend fun getOnlinePaymentData(): Results<String>
}
