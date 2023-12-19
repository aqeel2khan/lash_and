package com.cosmetics.domain.interaction.order

import com.cosmetics.domain.model.Results
import com.cosmetics.domain.repository.CartRepository

class GetKnetPayDetailsUseCaseImpl(
    private val cartRepository: CartRepository
) : GetKnetPayDetailsUseCase {
    override suspend fun invoke(emptyString: String): Results<String> =
        cartRepository.getOnlinePaymentData()
}