package com.cosmetics.lush.ui.products.search

import android.os.Handler
import android.util.Log
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.data.utils.CANCELLATION_EXCEPTION
import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.domain.interaction.product.cart.AddToWishListUseCase
import com.cosmetics.domain.interaction.product.cart.SearchProductUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.ProductListResponse
import com.cosmetics.lush.BR
import kotlinx.coroutines.Job
class SearchViewModel(
    private val searchProductUseCase: SearchProductUseCase,
    private val addToWishListUseCase: AddToWishListUseCase
) : BaseViewModel() {
    private var searchJob: Job? = null
    private var mHandler: Handler = Handler()
    var productList = MutableLiveData<ProductListResponse>()
    var recentSearchList = MutableLiveData<List<String>>()
    var searchString = MutableLiveData<String>()
    var isRecentSearchListVisible: MutableLiveData<Boolean> = MutableLiveData(true)
    var isSearchProgressVisible: MutableLiveData<Boolean> = MutableLiveData(false)
    var onClearProductList: MutableLiveData<Boolean> = MutableLiveData(false)
    override fun getBindingId(): Int = BR.viewModel
    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        Log.w("onTextChanged", " $s")
        if (s.isEmpty()) {
            clearText()
            isSearchProgressVisible.value = false
            return
        }
        val queryString = s.toString()
        isSearchProgressVisible.value = true
        mHandler.removeCallbacksAndMessages(null)
        mHandler.postDelayed({ callSearchApi(queryString) }, 500)
    }

    private fun callSearchApi(s: String) {
        executeSearchUseCase(false) {
            searchProductUseCase(s)
                .onSuccess { productListResponse ->
                    viewState.value = Completed
                    val response = Results.Success(productListResponse).data
                    productList.value = response
                    saveToRecentSearch(productListResponse)
                    isSearchProgressVisible.value = false
                }
                .onFailure {
                    if (it != CANCELLATION_EXCEPTION) {
                        isSearchProgressVisible.value = false
                    }
                    handleError(it) {
                        callSearchApi(s)
                    }
                }
        }
    }

    private fun saveToRecentSearch(productListResponse: ProductListResponse) {
        var recentSearchList: MutableSet<String> = mutableSetOf()
        if (PreferenceDelegate.recentSearchList.isNotEmpty()) {
            var list = PreferenceDelegate.recentSearchList.split(",")
            recentSearchList = list.toMutableSet()
        }
        recentSearchList.add(productListResponse.searchKey.trim())
        this.recentSearchList.value = recentSearchList.toMutableList().takeLast(4)
        this.recentSearchList.value?.joinToString(",")?.let {
            PreferenceDelegate.recentSearchList = it
        }
    }

    fun expandRecentSearch() {
        isRecentSearchListVisible.value = !isRecentSearchListVisible.value!!
    }

    fun clearText() {
        searchString.value = ""
        searchJob?.cancel()
        productList.value?.let {
            if (!it.products.isNullOrEmpty()) {
                productList.value?.products?.clear()
            }
        }
        onClearProductList.value = !onClearProductList.value!!
    }

    private fun executeSearchUseCase(isLoading: Boolean = true, action: suspend () -> Unit) {
        searchJob?.cancel()
        searchJob = launch { action() }
    }

    fun addToWishList(id: Int): MutableLiveData<Boolean> {
        var isSuccess = MutableLiveData<Boolean>()
        executeUseCase {
            addToWishListUseCase(id)
                .onSuccess {
                    viewState.value = Completed
                    isSuccess.value = true
                }
                .onFailure {
                    handleError(it)
                    isSuccess.value = false
                }
        }
        return isSuccess
    }

}

@BindingAdapter("setSearchText")
fun setSearchText(view: EditText, value: String?) {
    value?.let {
        if (it.isNotEmpty()) {
            view.setSelection(it.length)
        }
    }
}