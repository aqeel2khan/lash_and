package com.cosmetics.lush.ui.homemodule.categories

import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.domain.interaction.categories.GetCategoryUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.request.HomeResponse

class CategoriesViewModel(val getCategoryUseCase: GetCategoryUseCase) : BaseViewModel() {
    var categoryData = MutableLiveData<HomeResponse>()
    fun getCategories(): MutableLiveData<HomeResponse> {
        executeUseCase {
            getCategoryUseCase("")
                .onSuccess {
                    val response = Results.Success(it).data
                    categoryData.value = response
                    viewState.value = Completed
                }
                .onFailure {
                    handleError(it) { getCategories() }
                }
        }
        return categoryData
    }

    override fun getBindingId(): Int = 0
}
