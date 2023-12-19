package com.cosmetics.lush.ui.homemodule.lush_store

import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.domain.interaction.store.GetLushStoreUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.store.Store

class LushStoreViewModel(private val getLushStoreUseCase: GetLushStoreUseCase) : BaseViewModel() {
    init {
        getStoreList()
    }

    val storeList: MutableLiveData<List<Store>> = MutableLiveData()
    override fun getBindingId(): Int = 0
    private fun getStoreList() {
        executeUseCase {
            getLushStoreUseCase("")
                .onSuccess {
                    viewState.value = Completed
                    val response = Results.Success(it).data
                    storeList.value = response.store
                }
                .onFailure {
                    handleError(it) {
                        getStoreList()
                    }
                }
        }
    }
}
