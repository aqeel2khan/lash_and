package com.cosmetics.data.networking.service

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.home.delivermethods.DeliveryMethodRequest
import com.cosmetics.domain.model.home.delivermethods.DeliveryMethodResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DeliveryMethodService {

    @GET("index.php?route=rest/shipping_method/shippingmethods")
    suspend fun getDeliveryMethod(): Response<DeliveryMethodResponse>

    @POST("index.php?route=rest/shipping_method/shippingmethods")
    suspend fun setDeliveryMethod(
        @Body deliveryMethodRequest: DeliveryMethodRequest
    ): Response<BaseResponse>
}

