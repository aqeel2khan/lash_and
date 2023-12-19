package com.cosmetics.data.repository.home.product

import com.cosmetics.data.networking.service.HomeService
import com.cosmetics.data.utils.safeApiCall
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.interaction.product.cart.AddToCartResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.profile.CartCountResponse
import com.cosmetics.domain.model.product.ProductListRequest
import com.cosmetics.domain.model.product.ProductListResponse
import com.cosmetics.domain.model.product.cart.*
import com.cosmetics.domain.model.product.review.PostReviewRequest
import com.cosmetics.domain.model.product.wishlist.ProductWishListResponse
import com.cosmetics.domain.repository.ProductRepository
import com.cosmetics.domain.repository.WishListRepository

class ProductsRepositoryImpl(private val homeService: HomeService) :
    ProductRepository {

    override suspend fun getProductList(categoryId: Int): Results<ProductListResponse> =
        safeApiCall { homeService.getProductList(categoryId) }

    override suspend fun getProductSortedList(productListRequest: ProductListRequest): Results<ProductListResponse> {
        return safeApiCall {
            homeService.getProductSortedList(
                categoryId = productListRequest.categoryId,
                sort = productListRequest.sortType,
                order = productListRequest.sortOrder,
                page = productListRequest.pageNumber
            )
        }
    }

    override suspend fun getProduct(id: Int) =
        safeApiCall { homeService.getProduct(id) }

    override suspend fun getRelatedProduct(productId: Int) =
        safeApiCall { homeService.getRelatedProduct(productId) }

    override suspend fun searchProduct(productName: String): Results<ProductListResponse> {
        var response = safeApiCall {
            homeService.searchProduct(productName)
        }
        if (response is Results.Success) {
            response.data.searchKey = productName
        }
        return response
    }

    override suspend fun getProductsFromCart(): Results<ProductInCartResponse> =
        safeApiCall { homeService.getCart() }

    override suspend fun getCartCount(): Results<CartCountResponse> =
        safeApiCall {
            homeService.getCartCount()
        }

    override suspend fun getConfirmedProduct(): Results<ConfirmedProductsResponse> =
        safeApiCall {
            homeService.getConfirmedProduct()
        }

    override suspend fun confirmedProduct(): Results<ConfirmOrderResponse> = safeApiCall {
        homeService.confirmedProduct()
    }

    override suspend fun getPaymentMethod(): Results<PaymentMethodResponse> = safeApiCall {
        homeService.getPaymentMethod()
    }

    override suspend fun setPaymentMethod(paymentMethodRequest: SetPaymentMethodRequest): Results<BaseResponse> =
        safeApiCall {
            homeService.setPaymentMethod(paymentMethodRequest)
        }

    override suspend fun postReview(postReviewRequest: PostReviewRequest): Results<BaseResponse> =
        safeApiCall {
            homeService.postReview(postReviewRequest.productId, postReviewRequest)
        }

    override suspend fun updateCart(updateCartRequest: UpdateCartRequest): Results<BaseResponse> =
        safeApiCall { homeService.updateCart(updateCartRequest) }

    override suspend fun addProductToCart(addProductToCartRequest: AddProductToCartRequest): Results<AddToCartResponse> =
        safeApiCall { homeService.addProductToCart(addProductToCartRequest) }

}

class WishListRepositoryImpl(private val homeService: HomeService) : WishListRepository {

    override suspend fun addToWishList(categoryId: Int): Results<ProductListResponse> =
        safeApiCall { homeService.addToWishList(categoryId) }

    override suspend fun getWishlistUseCase(): Results<ProductWishListResponse> =
        safeApiCall { homeService.getWishList() }

    override suspend fun removeFromWishList(categoryId: Int): Results<BaseResponse> =
        safeApiCall { homeService.removeFromWishList(categoryId) }

}