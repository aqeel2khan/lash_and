package com.cosmetics.lush.ui.products.wishlist

import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.domain.interaction.product.cart.GetWishListUseCase
import com.cosmetics.domain.interaction.product.cart.RemoveFromWishListUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.wishlist.ProductWishListResponse
import com.cosmetics.lush.BR

class WishListViewModel(
    private val getWishListUseCase: GetWishListUseCase,
    private val removeFromWishListUseCase: RemoveFromWishListUseCase
) : BaseViewModel() {
    override fun getBindingId() = BR.viewModel
    var productList = MutableLiveData<ProductWishListResponse>()
    var noRecordsFound = MutableLiveData(false)

    fun getProductWishList(): MutableLiveData<ProductWishListResponse> {
        executeUseCase {
            getWishListUseCase("")
                .onSuccess {
                    viewState.value = Completed
                    val response = Results.Success(it).data
                    if (response.productWishList.isNullOrEmpty()) {
                        noRecordsFound.value = true
                    } else {
                        noRecordsFound.value = false
                        productList.postValue(response)
                    }
                }
                .onFailure {
                    handleError(it) { getProductWishList() }
                }
        }
        return productList
    }

    fun removeFromWishList(productId: Int): MutableLiveData<Boolean> {
        var isSuccess = MutableLiveData<Boolean>()
        executeUseCase {
            removeFromWishListUseCase(productId)
                .onSuccess {
                    viewState.value = Completed
                    val response = Results.Success(it).data
                    isSuccess.value = true
                }
                .onFailure {
                    isSuccess.value = false
                    handleError(it)
                }
        }
        return isSuccess
    }

}
