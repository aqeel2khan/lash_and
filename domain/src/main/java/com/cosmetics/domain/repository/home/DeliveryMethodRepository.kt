package com.cosmetics.domain.repository.home

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.delivermethods.DeliveryMethodRequest
import com.cosmetics.domain.model.home.delivermethods.DeliveryMethodResponse

interface DeliveryMethodRepository {
    suspend fun getDeliveryMethod(): Results<DeliveryMethodResponse>
    suspend fun setDeliveryMethod(deliveryMethodRequest: DeliveryMethodRequest): Results<BaseResponse>
}