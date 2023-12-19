package com.cosmetics.domain.interaction.categories

import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.request.HomeResponse
import com.cosmetics.domain.repository.CategoriesRepository

class GetCategoryUseCaseImpl(
    private val categoriesRepository: CategoriesRepository
) : GetCategoryUseCase {

    override suspend operator fun invoke(emptyString: String): Results<HomeResponse> {
        return categoriesRepository.getCategories()
    }
}