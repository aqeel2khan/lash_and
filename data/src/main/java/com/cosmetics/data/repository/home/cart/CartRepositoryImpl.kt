package com.cosmetics.data.repository.home.cart

import com.cosmetics.data.networking.service.CartService
import com.cosmetics.data.utils.safeApiCall
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.cart.AddCouponRequest
import com.cosmetics.domain.repository.CartRepository

class CartRepositoryImpl(private val cartService: CartService) : CartRepository {
    override suspend fun addCouponToOrder(addCouponRequest: AddCouponRequest): Results<BaseResponse> =
        safeApiCall {
            cartService.addCouponToOrder(addCouponRequest)
        }

    override suspend fun removeCouponFromOrder(): Results<BaseResponse> = safeApiCall {
        cartService.removeCouponFromOrder()
    }

    override suspend fun getOnlinePaymentData(): Results<String> {
        val response = cartService.getOnlinePaymentData()
        return if (!response.body().toString().isNullOrEmpty()) {
            Results.Success(response.body()?.string()!!)
        } else {
            Results.Error("Something went wrong")
        }
    }
}