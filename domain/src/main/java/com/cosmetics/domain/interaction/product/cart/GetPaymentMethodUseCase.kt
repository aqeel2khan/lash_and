package com.cosmetics.domain.interaction.product.cart

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.cart.PaymentMethodResponse
import com.cosmetics.domain.model.product.cart.SetPaymentMethodRequest

interface GetPaymentMethodUseCase : BaseUseCase<String, PaymentMethodResponse> {
    override suspend operator fun invoke(emptyString: String)
            : Results<PaymentMethodResponse>
}

interface SetPaymentMethodUseCase : BaseUseCase<SetPaymentMethodRequest, BaseResponse> {
    override suspend operator fun invoke(setPaymentMethodRequest: SetPaymentMethodRequest)
            : Results<BaseResponse>
}