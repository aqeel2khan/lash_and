package com.cosmetics.lush.ui.homemodule.cart

import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.domain.base.NO_DATA_FOUND
import com.cosmetics.domain.interaction.product.cart.AddCouponToOrderUseCase
import com.cosmetics.domain.interaction.product.cart.GetProductFromCartUseCase
import com.cosmetics.domain.interaction.product.cart.RemoveCouponFromOrderUseCase
import com.cosmetics.domain.interaction.product.cart.UpdateCartUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.cart.AddCouponRequest
import com.cosmetics.domain.model.product.cart.ItemList
import com.cosmetics.domain.model.product.cart.UpdateCartRequest
import com.cosmetics.lush.BR
import com.cosmetics.lush.R
import com.cosmetics.lush.base.IAppResources

class MyCartViewModel(
    private val cartUseCase: GetProductFromCartUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val addCouponToOrderUseCase: AddCouponToOrderUseCase,
    private val removeCouponFromOrderUseCase: RemoveCouponFromOrderUseCase,
    private val context: IAppResources
) : BaseViewModel() {
    private val couponNotApplied = 0
    val couponApplied = 1
    var productList = MutableLiveData<ItemList>()
    var isProductListVisible = MutableLiveData(false)
    var errorMessage = MutableLiveData<String>()
    var noProductMessage = MutableLiveData<String>()
    var totalPrice = MutableLiveData<String>("")
    var updateCart = MutableLiveData<UpdateCartRequest>()
    var coupon = MutableLiveData<String>()
    var message = MutableLiveData<String>()
    var promoCodeMessage = MutableLiveData<String>("")
    var couponStatus = MutableLiveData<Int>(couponApplied)
    var totalProductPrice: Double = 0.0
    var isShippingAddressNeeded = true
    override fun getBindingId(): Int = BR.viewModel
    fun getProductFromCart(): MutableLiveData<ItemList> {
        executeUseCase {
            cartUseCase("")
                .onSuccess {
                    viewState.value = Completed
                    isProductListVisible.value = true
                    val response = Results.Success(it).data
                    totalPrice.value = response.item.getTotalAmount()
                    productList.value = response.item
                    totalProductPrice = response.item.totalRaw
                    isShippingAddressNeeded = response.item.hasShipping != 0
                    setupCoupon(response.item)
                    if (!it.error.isNullOrEmpty()) {
                        message.value = it.error[0]
                    } else {
                        message.value = ""
                    }
                    errorMessage.value = ""
                }
                .onFailure {
                    isProductListVisible.value = false
                    message.value = ""
                    if (it.equals(NO_DATA_FOUND, true))
                        errorMessage.value = context.getString(R.string.shopping_cart_empty_message)
                    else errorMessage.value = it
                    viewState.value = Completed
                }
        }
        return productList
    }

    private fun setupCoupon(item: ItemList) {
        if (item.coupon.isNullOrEmpty()) {
            couponStatus.value = couponNotApplied
        } else {
            couponStatus.value = couponApplied
        }
        coupon.value = item.coupon
    }

    fun updateCart(updateCartRequest: UpdateCartRequest): MutableLiveData<UpdateCartRequest> {
        dismissSnackBar.value = true
        executeUseCase {
            updateCartUseCase(updateCartRequest)
                .onSuccess {
                    //viewState.value = Completed
                    updateCart.value = updateCartRequest
                    getProductFromCart()
                }
                .onFailure {
                    handleError(it) {
                        updateCart(updateCartRequest)
                    }
                }
        }
        return updateCart
    }

    fun handleCouponClick() {
        when {
            coupon.value.isNullOrEmpty() -> {
                handleError(context.getString(R.string.please_enter_a_coupon_code))
            }
            couponApplied == couponStatus.value -> {
                removeCouponFromOrder()
            }
            else -> {
                addCouponToOrder()
            }
        }
    }

    private fun addCouponToOrder() {
        coupon.value?.let {
            executeUseCase {
                addCouponToOrderUseCase(AddCouponRequest(it))
                    .onSuccess {
                        couponStatus.value = couponApplied
                        promoCodeMessage.value =
                            context.getString(R.string.your_coupon_discount_has_been_applied)
                        getProductFromCart()
                    }
                    .onFailure {
                        handleError(it)
                    }
            }
        }
    }

    private fun removeCouponFromOrder() {
        executeUseCase {
            removeCouponFromOrderUseCase("")
                .onSuccess {
                    couponStatus.value = couponNotApplied
                    coupon.value = ""
                    getProductFromCart()
                }
                .onFailure {
                    handleError(it)
                }
        }
    }
}
