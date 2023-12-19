package com.cosmetics.lush.ui.products.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.cosmetics.domain.di.getKoinInstance
import com.cosmetics.domain.interaction.product.cart.GetProductSortedListUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.Product
import com.cosmetics.domain.model.product.ProductListRequest
import com.cosmetics.lush.utils.PagingState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class ProductListDataSourceFactory(
    private var productListRequest: ProductListRequest,
    private val retryExecutor: Executor
) : DataSource.Factory<Int, Product>() {
    private val getProductSortedListUseCase: GetProductSortedListUseCase = getKoinInstance()
    private var sourceLiveData: MutableLiveData<ProductListDataSource>? = null

    init {
        sourceLiveData = MutableLiveData<ProductListDataSource>()
    }

    override fun create(): DataSource<Int, Product> {
        var productListDataSource = ProductListDataSource(
            productListRequest, retryExecutor, getProductSortedListUseCase
        )
        sourceLiveData?.postValue(productListDataSource)
        return productListDataSource
    }

    fun getDataSourceSource(): MutableLiveData<ProductListDataSource> = sourceLiveData!!
    fun setSortType(type: String, order: String) {
        productListRequest.sortType = type
        productListRequest.sortOrder = order
        productListRequest.pageNumber = 1
        sourceLiveData?.value?.setSortType(type, order)
    }

}

class ProductListDataSource(
    private var productListRequest: ProductListRequest,
    private val retryExecutor: Executor,
    private val getProductSortedListUseCase: GetProductSortedListUseCase
) : PageKeyedDataSource<Int, Product>() {

    private var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<PagingState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Product>
    ) {
        GlobalScope.launch {
            networkState.postValue(PagingState.InitialLoading)
            when (val response = getProductSortedListUseCase(productListRequest)) {
                is Results.Success -> {
                    val items = response.data.products as List<Product>
                    retry = null
                    networkState.postValue(PagingState.Completed)
                    callback.onResult(items, 1, 2)
                }
                is Results.Error -> {
                    retry = {
                        loadInitial(params, callback)
                    }
                    networkState.postValue(PagingState.Failed(response.message))
                }
            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Product>) {
        GlobalScope.launch {
            productListRequest.pageNumber = params.key
            networkState.postValue(PagingState.Loading)
            when (val response = getProductSortedListUseCase(
                productListRequest
            )) {
                is Results.Success -> {
                    val items = response.data.products as List<Product>
                    retry = null
                    networkState.postValue(PagingState.Completed)
                    callback.onResult(items, params.key + 1)
                }
                is Results.Error -> {
                    retry = {
                        loadAfter(params, callback)
                    }
                    networkState.postValue(PagingState.Failed(response.message))
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Product>) {
    }

    fun getNetworkState(): LiveData<PagingState> = networkState

    fun setSortType(type: String, order: String) {
        productListRequest.sortType = type
        productListRequest.sortOrder = order
    }

}