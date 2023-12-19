package com.cosmetics.domain.interaction.store

import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.store.StoreListResponse
import com.cosmetics.domain.repository.home.StoreRepository

interface GetLushStoreUseCase : BaseUseCase<String, StoreListResponse> {

    override suspend operator fun invoke(emptyString: String): Results<StoreListResponse>

}

class GetLushStoreUseCaseImpl(private val storeRepository: StoreRepository) : GetLushStoreUseCase {
    override suspend fun invoke(emptyString: String): Results<StoreListResponse> =
        storeRepository.getStoreList()
}