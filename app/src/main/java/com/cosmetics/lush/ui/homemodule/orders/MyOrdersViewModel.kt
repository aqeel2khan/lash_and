package com.cosmetics.lush.ui.homemodule.orders

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.core.utils.Loading
import com.cosmetics.core.utils.ViewState
import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.domain.model.order.Order
import com.cosmetics.lush.BR
import com.cosmetics.lush.R
import com.cosmetics.lush.ui.getUserName
import com.cosmetics.lush.ui.homemodule.orders.paging.OrderHistoryDataSourceFactory
import com.cosmetics.lush.ui.products.PAGING_SIZE
import com.cosmetics.lush.utils.PagingState
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class MyOrdersViewModel(private val context: Context) : BaseViewModel() {
    var name = MutableLiveData(getProfileName())
    var email = MutableLiveData(PreferenceDelegate.email)
    val isUserLoggedIn = MutableLiveData(!PreferenceDelegate.customerId.isNullOrEmpty())
    private val networkExecutor = Executor { command -> command.run() }
    var postLiveData: LiveData<PagedList<Order>>? = null
    private var _netWorkState: LiveData<PagingState>? = null
    private lateinit var ordDataSourceFactory: OrderHistoryDataSourceFactory
    private var netWorkState: LiveData<ViewState>? = null

    override fun getBindingId(): Int = BR.viewModel

    private fun getProfileName(): String {
        if (PreferenceDelegate.customerId.isNullOrBlank()) {
            return context.getString(R.string.guest)
        }
        return getUserName()
    }

    fun refreshPage() {
        name.value = getUserName()
        email.value = PreferenceDelegate.email
        isUserLoggedIn.value = true
        ordDataSourceFactory.getDataSourceSource().value?.invalidate()
    }

    fun initPagingList() {
        val executor = Executors.newFixedThreadPool(5)
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(PAGING_SIZE)
            .setPageSize(PAGING_SIZE)
            .build()
        ordDataSourceFactory = OrderHistoryDataSourceFactory(networkExecutor)
        setupObservers()
        postLiveData = LivePagedListBuilder(ordDataSourceFactory, pagedListConfig)
            .setFetchExecutor(executor!!)
            .build()
    }

    private fun setupObservers() {
        _netWorkState = Transformations.switchMap(ordDataSourceFactory.getDataSourceSource()) {
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

    fun getNetworkState(): LiveData<ViewState>? {
        return netWorkState
    }

    fun retry() {
        ordDataSourceFactory.getDataSourceSource().value?.retryAllFailed()
    }
}
