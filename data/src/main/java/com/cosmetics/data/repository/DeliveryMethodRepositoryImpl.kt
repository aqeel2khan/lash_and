package com.cosmetics.data.repository

import com.cosmetics.data.networking.service.DeliveryMethodService
import com.cosmetics.data.utils.safeApiCall
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.delivermethods.DeliveryMethodRequest
import com.cosmetics.domain.model.home.delivermethods.DeliveryMethodResponse
import com.cosmetics.domain.repository.home.DeliveryMethodRepository

class DeliveryMethodRepositoryImpl(private val deliveryMethodService: DeliveryMethodService) :
    DeliveryMethodRepository {
    override suspend fun getDeliveryMethod(): Results<DeliveryMethodResponse> =
        safeApiCall {
            deliveryMethodService.getDeliveryMethod()
        }

    override suspend fun setDeliveryMethod(data: DeliveryMethodRequest): Results<BaseResponse> =
        safeApiCall {
            deliveryMethodService.setDeliveryMethod(data)
        }
}