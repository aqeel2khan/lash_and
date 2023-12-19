package com.cosmetics.lush.ui.homemodule.orders.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.core.utils.Loading
import com.cosmetics.core.utils.OrderStatusItem
import com.cosmetics.domain.interaction.order.GetMyOrderDetailUseCase
import com.cosmetics.domain.interaction.order.GetOrderStatusUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.order.History
import com.cosmetics.domain.model.order.Order
import com.cosmetics.domain.model.order.OrderDetail
import com.cosmetics.domain.model.order.OrderStatus
import com.cosmetics.lush.BR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

const val ORDER_STATUS_COMPLETED = 5

class MyOrderDetailsViewModel(
    private val getMyOrderDetailUseCase: GetMyOrderDetailUseCase,
    private val getOrderStatusUseCase: GetOrderStatusUseCase
) : BaseViewModel() {

    private var orderStatusMasterList = mutableListOf<OrderStatus>()
    var orderStatusList: MutableLiveData<MutableList<OrderStatusItem>> = MutableLiveData()
    var orderStatuss: MutableLiveData<MutableList<History>> = MutableLiveData()
    var order: Order? = null
    var orderDetail = MutableLiveData<OrderDetail>()
    var isDateAvailable = MutableLiveData(false)
    var isTotalCostAvailable = MutableLiveData(false)

    init {
        initAPICall()
    }


    override fun getBindingId(): Int = BR.viewModel

    fun initAPICall() {
        order?.orderId?.let {
            viewState.value = Loading
            viewModelScope.launch {
                //parallel calls
                val diff = listOf(
                    async(Dispatchers.IO) { getMyOrderDetailAsync(it) },
                    async(Dispatchers.IO) { getOrderStatusAsync() }
                )
                diff.awaitAll()
                initOrderStatusList()
            }
        }
    }

    private fun initOrderStatusList() {
        //https://kotlinlang.org/docs/reference/collection-parts.html#take-and-drop
        //order details and order status are received.
        orderDetail.value?.let { orderDetail ->
            var orderHistory = orderDetail.histories
            if (!orderStatusMasterList.isNullOrEmpty() && !orderHistory.isNullOrEmpty()) {
                orderHistory.forEach { it.isFound = true }
                val commonOrderStatusList =
                    orderStatusMasterList.dropLastWhile { it.orderStatusId != ORDER_STATUS_COMPLETED }
                if (!commonOrderStatusList.isNullOrEmpty() && commonOrderStatusList.any {
                        it.orderStatusId == orderDetail.orderStatusId
                    }) {//Indicates there is status called completed
                    var futureStatus = commonOrderStatusList.takeLastWhile {
                        it.orderStatusId != orderDetail.orderStatusId
                    }
                    if (!futureStatus.isNullOrEmpty()) {//Indicates there is status called completed
                        futureStatus.forEach {
                            orderHistory.add(History(status = it.name, isFound = false))
                        }
                    }
                }
                orderStatuss.value = orderHistory
            }
        }

    }
/*

    private fun initOrderStatusList() {
        //order details and order status are received.
        orderDetail.value?.let { orderDetail ->
            if (!orderStatusMasterList.isNullOrEmpty()) {
                var orderList: MutableList<OrderStatusItem> = mutableListOf()
                var orderStatus = orderStatusMasterList.findLast {
                    it.orderStatusId == ORDER_STATUS_COMPLETED
                }

                //     println("Collection test"+orderStatusMasterList.dropLastWhile { it.orderStatusId != ORDER_STATUS_COMPLETED })
                if (orderStatus != null) {//Indicates there is status called completed
                    var index =
                        orderStatusMasterList.indexOf(orderStatus) // find the index of completed status
                    if (index >= 0) {
                        var commonOrderStatusList: MutableList<OrderStatus> =
                            orderStatusMasterList.subList(
                                0, index + 1
                            )// Splist the list. Remove all other status after completed
                        orderList = setupCurrentOrderStatusList(
                            commonOrderStatusList.any {
                                it.orderStatusId == orderDetail.orderStatusId
                            },
                            commonOrderStatusList,
                            orderDetail,
                            orderList
                        )
                    } else {
                        orderDetail.histories.forEach {
                            orderList.add(OrderStatusItem(it.status, true))
                        }
                    }
                    orderStatusList.value = orderList
                }

            }
        }

    }
*/

    private fun setupCurrentOrderStatusList(
        isCurrentOrderStatusAvailable: Boolean,
        commonOrderStatusList: MutableList<OrderStatus>,
        orderDetail: OrderDetail,
        orderList: MutableList<OrderStatusItem>
    ): MutableList<OrderStatusItem> {
        if (!orderStatusList.value.isNullOrEmpty()) orderStatusList.value?.clear() //As a precaution, Clear the list
        var isFound = true
        if (isCurrentOrderStatusAvailable) {
            commonOrderStatusList.forEach {
                orderList.add(OrderStatusItem(it.name, isFound))
                if (it.orderStatusId == orderDetail.orderStatusId) isFound = false
            }
        } else {
            orderDetail.histories.forEach {
                orderList.add(OrderStatusItem(it.status, true))
            }
        }
        return orderList
    }

    private suspend fun getMyOrderDetailAsync(orderId: String) {
        getMyOrderDetailUseCase(orderId)
            .onSuccess {
                viewState.postValue(Completed)
                val response = Results.Success(it).data
                if (response.data.timestamp != null) {
                    isDateAvailable.postValue(true)
                }
                if (!response.data.total.isNullOrEmpty()) {
                    isTotalCostAvailable.postValue(true)
                }
                orderDetail.postValue(response.data)
            }
            .onFailure {
                handleErrorAsync(it) {
                    initAPICall()
                }
            }
    }

    private suspend fun getOrderStatusAsync() {
        getOrderStatusUseCase("")
            .onSuccess {
                viewState.postValue(Completed)
                val response = Results.Success(it).data
                orderStatusMasterList = response.orderStatus as MutableList<OrderStatus>
            }
            .onFailure {
                handleErrorAsync(it) {
                    initAPICall()
                }
            }
    }

}
