package com.cosmetics.lush.ui.products.product_details

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.core.utils.Event
import com.cosmetics.core.utils.Loading
import com.cosmetics.domain.di.getKoinInstance
import com.cosmetics.domain.interaction.product.cart.*
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.*
import com.cosmetics.domain.model.product.Product
import com.cosmetics.domain.model.product.cart.AddProductToCartRequest
import com.cosmetics.domain.model.product.wishlist.ProductWishList
import com.cosmetics.lush.BR
import com.cosmetics.lush.R
import com.cosmetics.lush.ui.products.filterMoreInCategory
import com.cosmetics.lush.ui.products.product_details.model.QuantityModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val GetProductWithCategoryUseCase: GetProductWithCategoryUseCase,
    private val GetRelatedProductListUseCase: GetRelatedProductListUseCase,
    private val addToWishListUseCase: AddToWishListUseCase,
    private val getProductListUseCase: GetProductListUseCase,
    private val getWishListUseCase: GetWishListUseCase,
    private val removeFromWishListUseCase: RemoveFromWishListUseCase
) : BaseViewModel() {
    val context: Context = getKoinInstance()
    var productsMoreInCategory = MutableLiveData<List<Product>>()
    var discountAppliedObsever = MutableLiveData<Event<Boolean>>()
    var totalAmount = MutableLiveData<String>()
    var product: MutableLiveData<Product> = MutableLiveData()//MasterModel
    var quantityModel: MutableLiveData<QuantityModel> = MutableLiveData(QuantityModel())
    var attributeListLiveData: MutableLiveData<List<Attribute>> = MutableLiveData()
    var discountList = MutableLiveData<List<Discount>>()
    var quantity: String = "1"
    var productId: Int? = null//
    var categoryId: Int? = null//
    var reviewItem: MutableLiveData<ReviewItem> = MutableLiveData()
    var relatedProductRespone: ProductListResponse? = null
    var productWishListStatus: MutableLiveData<Int> = MutableLiveData(WISH_LIST_GONE)
    var isRelatedProductLoadingProgress: MutableLiveData<Boolean> = MutableLiveData(true)
    var isMoreProductInCategoryLoadingProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    private var relatedProduct: MutableLiveData<List<Product>> = MutableLiveData()
    var isSuccess = MutableLiveData<Boolean>()
    var isRelatedProductVisible = MutableLiveData<Boolean>(true)
    var isMoreProductInCategoryVisible = MutableLiveData<Boolean>(true)
    var isProductReviewListVisibile = MutableLiveData<Boolean>(false)
    private var productWishList = mutableListOf<ProductWishList>()

    override fun getBindingId(): Int = BR.productDetailViewModel

    fun initAPICall() {
        viewState.value = Loading
        viewModelScope.launch {
            val diff = listOf(
                async(Dispatchers.IO) { getProductDetailsAsync() },
                async(Dispatchers.IO) { getProductWishListAsync() }
            )
            diff.awaitAll()
            viewState.postValue(Completed)
            initWishList()
        }
    }

    private suspend fun getProductDetailsAsync() {
        productId?.let { it ->
            GetProductWithCategoryUseCase(it)
                .onSuccess { item ->
                    initProductDetails(item.data)
                    initProductReview(item.data)
                    initProductAttributeList(item.data.attributeGroups)
                }
                .onFailure {
                    handleErrorAsync(it) {
                        if (it == context.getString(R.string.no_network)) {
                            retryAllCalls()
                        }
                        initAPICall()
                    }
                }
        }
    }

    private fun initProductAttributeList(attributeGroupList: List<AttributeGroup>) {
        if (!attributeGroupList.isNullOrEmpty()) {
            val attributeList = mutableListOf<Attribute>()
            attributeGroupList.forEach { attributeGroup ->
                attributeGroup.attributes.let { attributes ->
                    if (!attributes.isNullOrEmpty()) {
                        attributes.forEach {
                            attributeList.add(it)
                        }
                    }
                }
            }
            if (!attributeList.isNullOrEmpty()) {
                attributeListLiveData.postValue(attributeList)
            }
        }
    }

    private fun retryAllCalls() {
        getProductListMoreInCategory()
        getRelatedProductList()
    }

    private fun initProductReview(product: Product?) {
        if (!product?.reviews?.reviewList.isNullOrEmpty()) {
            reviewItem.postValue(product?.reviews?.reviewList?.get(0))
            isProductReviewListVisibile.postValue(true)
        }
    }

    private suspend fun getProductWishListAsync() {
        getWishListUseCase("")
            .onSuccess {
                val response = Results.Success(it).data.productWishList
                productWishList = response as MutableList<ProductWishList>
            }
    }

    private fun initWishList() {
        productWishList.let { productWishList ->
            product.value?.let { productItem ->
                if (productWishList.isNullOrEmpty()) {
                    productWishListStatus.value = WISH_LIST_NOT_ADDED
                    return
                }
                var contain = productWishList.any {
                    it.productId == productItem.productId
                }
                if (contain) {
                    productWishListStatus.value = WISH_LIST_ADDED
                } else {
                    productWishListStatus.value = WISH_LIST_NOT_ADDED
                }
            }
        }
    }

    fun getRelatedProductList(): MutableLiveData<List<Product>> {
        executeUseCase(true) {
            productId?.let {
                GetRelatedProductListUseCase(it)
                    .onSuccess { item ->
                        isRelatedProductLoadingProgress.value = false
                        if (!item.products.isNullOrEmpty()) {
                            relatedProductRespone = item
                            isRelatedProductVisible.value = true
                            item.products?.let { product ->
                                if (product.size > 1) {
                                    relatedProduct.value = product.subList(0, 2)
                                } else {
                                    relatedProduct.value = product
                                }
                            }
                        } else {
                            isRelatedProductVisible.value = false
                        }
                    }
                    .onFailure {
                        isRelatedProductLoadingProgress.value = false
                        isRelatedProductVisible.value = false
                    }
            }
        }
        return relatedProduct
    }

    fun getProductListMoreInCategory(): MutableLiveData<List<Product>> {
        categoryId?.let {
            isMoreProductInCategoryLoadingProgress.value = true
            executeUseCase {
                getProductListUseCase(it)
                    .onSuccess { response ->
                        setupMoreInCategoryData(response)
                    }
                    .onFailure {
                        isMoreProductInCategoryLoadingProgress.value = false
                        isMoreProductInCategoryVisible.value = false
                    }
            }
        }
        return productsMoreInCategory
    }

    private fun setupMoreInCategoryData(response: ProductListResponse) {
        try {
            isMoreProductInCategoryLoadingProgress.value = false//Dismiss progress
            val moreInCategory =
                Results
                    .Success(response)
                    .data.products?.filterMoreInCategory(product.value!!)
            if (!moreInCategory.isNullOrEmpty() && moreInCategory.size > 0) {
                productsMoreInCategory.value = moreInCategory
                isMoreProductInCategoryVisible.value = true
            } else {
                isMoreProductInCategoryVisible.value = false
            }
        } catch (e: Exception) {
        }
    }

    fun getCategoryID(): ProductCategory? {
        if (!product.value?.category.isNullOrEmpty()) {
            return product.value?.category?.get(0)
        }
        return null
    }

    fun getReviewData(): Reviews? {
        product.value?.reviews?.let { review ->
            review.reviewList?.let {
                if (it.isNotEmpty() && it.size > 1) {
                    return review
                }
            }
        }
        return null
    }

    fun addToCart(): MutableLiveData<AddToCartResponse> {
        val response: MutableLiveData<AddToCartResponse> = MutableLiveData()
        product.value?.let {
            executeUseCase {
                addProductToCartUseCase(
                    AddProductToCartRequest(
                        productId = it.id,
                        quantity = quantity,
                        mutableMap = null
                    )
                )
                    .onSuccess {
                        viewState.value = Completed
                        response.value = Results.Success(it).data
                    }
                    .onFailure {
                        handleError(it)
                    }
            }
        }
        return response
    }

    private fun getMinimum(it: Product): String {
        if (!it.minimum.isNullOrEmpty() && it.minimum != "0") {
            return it.minimum!!
        }
        return "1"
    }

    fun handleWishListClick() {
        when (productWishListStatus.value) {
            WISH_LIST_ADDED -> {
                removeFromWishList()
            }
            WISH_LIST_NOT_ADDED -> {
                addToWishList()
            }
        }
    }

    private fun removeFromWishList(): MutableLiveData<Boolean> {
        var isSuccess = MutableLiveData<Boolean>()
        product.value?.productId?.let {
            executeUseCase {
                removeFromWishListUseCase(it)
                    .onSuccess {
                        viewState.value = Completed
                        productWishListStatus.value = WISH_LIST_NOT_ADDED
                        val response = Results.Success(it).data
                        isSuccess.value = true
                    }
                    .onFailure {
                        isSuccess.value = false
                    }
            }
        }
        return isSuccess
    }

    private fun addToWishList(): MutableLiveData<Boolean> {
        executeUseCase {
            product.value?.productId?.let {
                addToWishListUseCase(it)
                    .onSuccess {
                        viewState.value = Completed
                        productWishListStatus.value = WISH_LIST_ADDED
                        isSuccess.value = true
                    }
                    .onFailure {
                        handleError(it)
                        isSuccess.value = false
                    }
            }
        }
        return isSuccess
    }

    fun initProductDetails(item: Product?) {
        product.postValue(item)
        totalAmount.postValue(item?.getFinalePriceWithFormatted())
        quantityModel.value?.minimum = item?.let { getMinimum(it).toInt() } ?: 1
        quantityModel.value?.totalCount = item?.let { getMinimum(it).toInt() } ?: 1
        quantityModel.value?.countValue?.postValue(item?.let { getMinimum(it) })
        item?.let {
            if (it.isDiscountAvailable()) {
                discountList.postValue(it.discounts)
            }
        }
    }

    fun calculateTotalValue(count: Int) {
        product.value?.let {
            totalAmount.value = getPriceFormatted(it.getFinalePrice())
            if (count > 1) {
                val change = count - (it.minimum?.toInt() ?: 1)
                totalAmount.value =
                    getPriceFormatted(
                        ((change * getFinalPriceIncludingDiscount(it, count)) +
                                getFinalPriceIncludingDiscount(it, count))
                    )
            }
        }
    }


    private fun getFinalPriceIncludingDiscount(product: Product, count: Int): Float {
        var unitPrice = product.getFinalePrice()
        if (product.isDiscountAvailable()) {
            product.discounts.forEach {
                if (count >= it.quantity) {
                    unitPrice = it.price
                    if (!it.isDiscountShown) {
                        discountAppliedObsever.value = Event(true)
                        it.isDiscountShown = true
                    }
                } else if (count < it.quantity) {
                    it.isDiscountShown = false
                    return@forEach
                }
            }
        }
        return unitPrice
    }
}

