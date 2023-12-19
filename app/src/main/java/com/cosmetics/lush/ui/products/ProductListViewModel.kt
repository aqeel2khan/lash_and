package com.cosmetics.lush.ui.products

import android.os.Parcelable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.cosmetics.core.utils.Completed
import com.cosmetics.core.utils.Loading
import com.cosmetics.core.utils.ViewState
import com.cosmetics.domain.model.product.Product
import com.cosmetics.domain.model.product.ProductListRequest
import com.cosmetics.lush.BR
import com.cosmetics.lush.ui.products.paging.ProductListDataSourceFactory
import com.cosmetics.lush.utils.ObservableViewModel
import com.cosmetics.lush.utils.PagingState
import kotlinx.android.parcel.Parcelize
import java.util.concurrent.Executor
import java.util.concurrent.Executors


@Parcelize
class SimilarProductCategoryList : Parcelable {
    var productInSameCategory = mutableListOf<Product>()
}

internal const val PAGING_SIZE = 10
private const val SORT_ASC = "ASC"
private const val SORT_DESC = "DESC"
private const val PRODUCT_SORT_ORDER = "p.sort_order"
private const val PRODUCT_NAME_SORT_ORDER = "name"
private const val PRODUCT_PRICE_SORT_ORDER = "price"
private const val RATING_SORT_ORDER = "rating"
private const val PRODUCT_MODEL_SORT_ORDER = "model"

class ProductListViewModel : ObservableViewModel() {
    private var netWorkState: LiveData<ViewState>? = null
    private lateinit var productListDataSource: ProductListDataSourceFactory
    private val mapType = mapOf(
        0 to PRODUCT_SORT_ORDER,
        1 to PRODUCT_NAME_SORT_ORDER,
        2 to PRODUCT_NAME_SORT_ORDER,
        3 to PRODUCT_PRICE_SORT_ORDER,
        4 to PRODUCT_PRICE_SORT_ORDER,
        5 to RATING_SORT_ORDER,
        6 to RATING_SORT_ORDER,
        7 to PRODUCT_MODEL_SORT_ORDER,
        8 to PRODUCT_MODEL_SORT_ORDER
    )
    private val mapOrder = mapOf(
        0 to SORT_ASC, 1 to SORT_ASC, 2 to SORT_DESC, 3 to SORT_ASC, 4 to SORT_DESC,
        5 to SORT_DESC, 6 to SORT_ASC,
        7 to SORT_ASC, 8 to SORT_DESC
    )

    @JvmField
    var spinnerSelectedItemPosition: Int = 0
    var categoryId: Int = 0
    private var similarProductCategoryList = SimilarProductCategoryList()
    private val networkExecutor = Executor { command -> command.run() }
    var postLiveData: LiveData<PagedList<Product>>? = null
    private var _netWorkState: LiveData<PagingState>? = null

    override fun getBindingId(): Int = BR.viewModel

    @Bindable
    fun getSpinnerSelectedItemPosition(): Int {
        return spinnerSelectedItemPosition
    }

    fun setSpinnerSelectedItemPosition(selectedItemPosition: Int) {
        spinnerSelectedItemPosition = selectedItemPosition
        mapType[spinnerSelectedItemPosition]?.let { type ->
            mapOrder[spinnerSelectedItemPosition]?.let { order ->
                productListDataSource.setSortType(type, order)
            }
        }
        productListDataSource.getDataSourceSource().value?.invalidate()
        notifyPropertyChanged(BR.viewModel)
    }

    fun initPagingList() {
        val executor = Executors.newFixedThreadPool(5)
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(PAGING_SIZE)
            .setPageSize(PAGING_SIZE)
            .build()
        productListDataSource = ProductListDataSourceFactory(
            ProductListRequest(categoryId = categoryId),
            networkExecutor
        )
        setupObservers()
        postLiveData = LivePagedListBuilder(productListDataSource, pagedListConfig)
            .setFetchExecutor(executor!!)
            .build()
    }

    private fun setupObservers() {
        _netWorkState = Transformations.switchMap(productListDataSource.getDataSourceSource()) {
            it.getNetworkState()
        }
        netWorkState = Transformations.map(_netWorkState!!) { mapToViewState(it) }
    }

    private fun mapToViewState(it: PagingState) =
        when (it) {
            is PagingState.Loading -> {
                Loading
            }
            is PagingState.InitialLoading -> {
                viewState.value = Loading
                Completed

            }
            is PagingState.Completed -> {
                viewState.value = Completed
                Completed
            }
            is PagingState.Failed -> {
                viewState.value = Completed
                handleError(it.errorMsg)
            }
        }

    private fun initProductInSameCategory(products: MutableList<Product>) {
        with(similarProductCategoryList) {
            if (!products.isNullOrEmpty()) {
                if (products.size > 3) {
                    productInSameCategory.addAll(products.subList(0, 4))
                } else {
                    productInSameCategory.addAll(products)
                }
            }
        }
    }

    fun getProductInSameCategory(item: Product): SimilarProductCategoryList {
        postLiveData?.value?.let {
            val productList =
                (it as MutableList<Product>).filterMoreInCategory(item)
            if (productList != null) {
                similarProductCategoryList.productInSameCategory = productList
                return similarProductCategoryList
            }
        }
        /*    with(similarProductCategoryList) {
                if (!productInSameCategory.isNullOrEmpty()) {
                    productInSameCategory =
                        productInSameCategory.filter { item.id != it.id }.toMutableList()
                    if (productInSameCategory.size > 2) {
                        productInSameCategory = productInSameCategory.subList(0, 2)
                    }
                }
            }*/
        return similarProductCategoryList
    }

    fun getNetworkState(): LiveData<ViewState>? {
        return netWorkState
    }

    fun retry() {
        productListDataSource.getDataSourceSource().value?.retryAllFailed()
    }

}

fun MutableList<Product>.filterMoreInCategory(
    product: Product
): MutableList<Product>? {
    if (size > 0) {
        var productInSameCategory = filter { product.id != it.id }.toMutableList()
        if (productInSameCategory.size > 1) {
            productInSameCategory = productInSameCategory.subList(0, 2)
        }
        return productInSameCategory
    }
    return null
}