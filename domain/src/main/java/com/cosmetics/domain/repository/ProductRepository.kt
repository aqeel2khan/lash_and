package com.cosmetics.domain.repository

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.interaction.product.cart.AddToCartResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.profile.CartCountResponse
import com.cosmetics.domain.model.product.ProductItem
import com.cosmetics.domain.model.product.ProductListRequest
import com.cosmetics.domain.model.product.ProductListResponse
import com.cosmetics.domain.model.product.cart.*
import com.cosmetics.domain.model.product.review.PostReviewRequest
import com.cosmetics.domain.model.product.wishlist.ProductWishListResponse

interface ProductRepository {
    suspend fun getProductList(categoryId: Int): Results<ProductListResponse>
    suspend fun getCartCount(): Results<CartCountResponse>
    suspend fun getPaymentMethod(): Results<PaymentMethodResponse>
    suspend fun setPaymentMethod(setPaymentMethodRequest: SetPaymentMethodRequest): Results<BaseResponse>
    suspend fun postReview(postReviewRequest: PostReviewRequest): Results<BaseResponse>
    suspend fun getProductSortedList(productListRequest: ProductListRequest): Results<ProductListResponse>
    suspend fun getProduct(productId: Int): Results<ProductItem>
    suspend fun getRelatedProduct(productId: Int): Results<ProductListResponse>
    suspend fun searchProduct(categoryId: String): Results<ProductListResponse>
    suspend fun getProductsFromCart(): Results<ProductInCartResponse>
    suspend fun updateCart(updateCartRequest: UpdateCartRequest): Results<BaseResponse>
    suspend fun addProductToCart(addProductToCartRequest: AddProductToCartRequest): Results<AddToCartResponse>
    suspend fun getConfirmedProduct(): Results<ConfirmedProductsResponse>
    suspend fun confirmedProduct(): Results<ConfirmOrderResponse>
}


interface WishListRepository {
    suspend fun addToWishList(categoryId: Int): Results<ProductListResponse>
    suspend fun getWishlistUseCase(): Results<ProductWishListResponse>
    suspend fun removeFromWishList(categoryId: Int): Results<BaseResponse>
}