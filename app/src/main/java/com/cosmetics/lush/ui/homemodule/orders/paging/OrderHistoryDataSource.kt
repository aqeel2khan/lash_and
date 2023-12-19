package com.cosmetics.lush.ui.homemodule.orders.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.cosmetics.domain.base.NO_DATA_FOUND
import com.cosmetics.domain.di.getKoinInstance
import com.cosmetics.domain.interaction.profile.GetOrderHistoryUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.order.Order
import com.cosmetics.lush.utils.PagingState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class OrderHistoryDataSourceFactory(private val retryExecutor: Executor) :
    DataSource.Factory<Int, Order>() {
    private val getOrderHistoryUseCase: GetOrderHistoryUseCase = getKoinInstance()
    private var sourceLiveData: MutableLiveData<OrderHistoryDataSource>? = null

    init {
        sourceLiveData = MutableLiveData<OrderHistoryDataSource>()
    }

    override fun create(): OrderHistoryDataSource {
        var ordeDataSource = OrderHistoryDataSource(
            retryExecutor, getOrderHistoryUseCase
        )
        sourceLiveData?.postValue(ordeDataSource)
        return ordeDataSource
    }

    fun getDataSourceSource(): MutableLiveData<OrderHistoryDataSource> = sourceLiveData!!

}

class OrderHistoryDataSource(
    private val retryExecutor: Executor,
    private val getOrderHistoryUseCase: GetOrderHistoryUseCase
) : PageKeyedDataSource<Int, Order>() {

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
        callback: LoadInitialCallback<Int, Order>
    ) {
        GlobalScope.launch {
            networkState.postValue(PagingState.InitialLoading)
            when (val response = getOrderHistoryUseCase(
                1
            )) {
                is Results.Success -> {
                    val items = response.data.order
                    retry = null
                    if (!items.isNullOrEmpty()) {
                        networkState.postValue(PagingState.Completed)
                        callback.onResult(items, 1, 2)
                    } else {
                        retry = {
                            loadInitial(params, callback)
                        }
                        networkState.postValue(PagingState.Failed(NO_DATA_FOUND))
                    }
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

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Order>) {
        GlobalScope.launch {
            networkState.postValue(PagingState.Loading)
            when (val response = getOrderHistoryUseCase(
                params.key
            )) {
                is Results.Success -> {
                    val items = response.data.order
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

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Order>) {
    }

    fun getNetworkState(): LiveData<PagingState> = networkState

}