package com.cosmetics.data.networking.service

import com.cosmetics.domain.model.cart.AddCouponRequest
import com.cosmetics.domain.model.home.request.HomeResponse
import com.cosmetics.domain.model.product.ProductListResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface CartService {
    @POST("index.php?route=rest/cart/coupon")
    suspend fun addCouponToOrder(@Body addCouponRequest: AddCouponRequest): Response<HomeResponse>

    @DELETE("index.php?route=rest/cart/coupon")
    suspend fun removeCouponFromOrder(): Response<ProductListResponse>

    @GET("index.php?route=rest/confirm/confirm&page=pay")
    suspend fun getOnlinePaymentData(): Response<ResponseBody>
}

