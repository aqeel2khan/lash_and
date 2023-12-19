package com.cosmetics.lush.ui.homemodule.home

import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.domain.interaction.categories.GetCategoryUseCase

class HomeViewModel(val getCategoryUseCase: GetCategoryUseCase) :
    BaseViewModel() {


    override fun getBindingId(): Int = 0

}
