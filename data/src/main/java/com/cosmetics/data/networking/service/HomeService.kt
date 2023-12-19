package com.cosmetics.data.networking.service

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.interaction.product.cart.AddToCartResponse
import com.cosmetics.domain.model.home.address.CountryListResponse
import com.cosmetics.domain.model.home.address.ZoneListResponse
import com.cosmetics.domain.model.home.banner.HomeBannerResponse
import com.cosmetics.domain.model.home.contactus.ContactRequest
import com.cosmetics.domain.model.home.guest.CreateGuestUserRequest
import com.cosmetics.domain.model.home.profile.*
import com.cosmetics.domain.model.home.request.HomeResponse
import com.cosmetics.domain.model.home.store.StoreListResponse
import com.cosmetics.domain.model.order.MyOrderDetailsResponse
import com.cosmetics.domain.model.order.OrderHistoryResponse
import com.cosmetics.domain.model.order.OrderStatusResponse
import com.cosmetics.domain.model.product.ProductItem
import com.cosmetics.domain.model.product.ProductListResponse
import com.cosmetics.domain.model.product.cart.*
import com.cosmetics.domain.model.product.review.PostReviewRequest
import com.cosmetics.domain.model.product.wishlist.ProductWishListResponse
import retrofit2.Response
import retrofit2.http.*

interface HomeService {
    @GET("index.php?route=feed/rest_api/categories&level=2")
    suspend fun getCategories(): Response<HomeResponse>

    @GET("index.php?route=feed/rest_api/products")
    suspend fun getProductList(
        @Query("category") categoryId: Int,
        @Query("limit") limit: Int = 3,
        @Query("page") page: Int = 1
    ): Response<ProductListResponse>

    @GET("index.php?route=feed/rest_api/products")
    suspend fun getProductSortedList(
        @Query("category") categoryId: Int,
        @Query("sort") sort: String = "pd.name",
        @Query("order") order: String = "ASC",
        @Query("limit") limit: Int = 5,
        @Query("page") page: Int = 1
    ): Response<ProductListResponse>

    @GET("index.php?route=feed/rest_api/products")
    suspend fun getProduct(@Query("id") id: Int): Response<ProductItem>

    @GET("index.php?route=feed/rest_api/related")
    suspend fun getRelatedProduct(@Query("id") id: Int): Response<ProductListResponse>

    @GET("index.php?route=feed/rest_api/products")
    suspend fun searchProduct(@Query("search") productName: String)
            : Response<ProductListResponse>


    @POST("index.php?route=rest/cart/cart")
    suspend fun addProductToCart(@Body addProductToCartRequest: AddProductToCartRequest): Response<AddToCartResponse>

    @PUT("index.php?route=rest/cart/cart")
    suspend fun updateCart(@Body updateCartRequest: UpdateCartRequest): Response<BaseResponse>

    @GET("index.php?route=rest/cart/cart")
    suspend fun getCart(): Response<ProductInCartResponse>

    @GET("index.php?route=feed/rest_api/order_statuses")
    suspend fun getOrderStatuses(): Response<OrderStatusResponse>

    @GET("index.php?route=rest/order/orders")
    suspend fun getOrderDetails(@Query("id") id: String): Response<MyOrderDetailsResponse>

    @POST("index.php?route=rest/wishlist/wishlist")
    suspend fun addToWishList(@Query("id") categoryId: Int): Response<ProductListResponse>

    @DELETE("index.php?route=rest/wishlist/wishlist")
    suspend fun removeFromWishList(@Query("id") categoryId: Int): Response<BaseResponse>

    @GET("index.php?route=rest/wishlist/wishlist")
    suspend fun getWishList(): Response<ProductWishListResponse>

    @POST("index.php?route=rest/contact/send")
    suspend fun submitContactUs(@Body contactRequest: ContactRequest): Response<BaseResponse>

    @PUT("index.php?route=rest/account/account")
    suspend fun updateUserProfile(@Body updateUserProfileRequest: UpdateUserProfileRequest):
            Response<BaseResponse>

    @POST("index.php?route=rest/guest/guest")
    suspend fun createGuestUser(@Body createGuestUserRequest: CreateGuestUserRequest):
            Response<BaseResponse>

    @GET("index.php?route=rest/account/address")
    suspend fun getAddress(): Response<AddressResponse>

    @GET("index.php?route=rest/account/account")
    suspend fun getUserProfile(): Response<UserProfileResponse>

    @GET("index.php?route=rest/order/orders")
    suspend fun getOrderHistory(
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1
    ): Response<OrderHistoryResponse>


    @GET("index.php?route=rest/cart/cartCount")
    suspend fun getCartCount(): Response<CartCountResponse>

    @POST("index.php?route=rest/confirm/confirm")
    suspend fun getConfirmedProduct(): Response<ConfirmedProductsResponse>

    @GET("index.php?route=rest/payment_method/payments")
    suspend fun getPaymentMethod(): Response<PaymentMethodResponse>

    @POST("index.php?route=rest/payment_method/payments")
    suspend fun setPaymentMethod(@Body paymentMethodRequest: SetPaymentMethodRequest)
            : Response<BaseResponse>

    @POST("index.php?route=feed/rest_api/reviews")
    suspend fun postReview(
        @Query("id") id: Int,
        @Body postReviewRequest: PostReviewRequest
    )
            : Response<BaseResponse>

    @PUT("index.php?route=rest/confirm/confirm")
    suspend fun confirmedProduct(): Response<ConfirmOrderResponse>

    @GET("index.php?route=feed/rest_api/countries")
    suspend fun getCountryList(): Response<CountryListResponse>

    @GET("index.php?route=feed/rest_api/countries")
    suspend fun getZoneList(@Query("id") id: String): Response<ZoneListResponse>

    @GET("index.php?route=rest/shipping_address/shippingaddress/address")
    suspend fun getDeliveryAddress(): Response<AddressResponse>

    @GET("index.php?route=rest/payment_address/paymentaddress")
    suspend fun getPaymentAddress(): Response<AddressResponse>


    @POST("index.php?route=rest/shipping_address/shippingaddress&existing=1")
    suspend fun editDeliveryAddress(
        @Body cartAddressRequest: SetCartAddressRequest
    ): Response<BaseResponse>

    @POST("index.php?route=rest/payment_address/paymentaddress&existing=1")
    suspend fun editPaymentAddress(
        @Body cartAddressRequest: SetCartAddressRequest
    ): Response<BaseResponse>

    @PUT("index.php?route=rest/account/address")
    suspend fun editAddress(
        @Query("id") addressId: String,
        @Body updateAddressRequest: UpdateAddressRequest
    ): Response<BaseResponse>

    @POST("index.php?route=rest/account/address")
    suspend fun addAddress(
        @Body updateAddressRequest: UpdateAddressRequest
    ): Response<BaseResponse>

    @POST("index.php?route=rest/shipping_address/shippingaddress")
    suspend fun addShippingAddress(
        @Body updateAddressRequest: UpdateAddressRequest
    ): Response<BaseResponse>

    @POST("index.php?route=rest/payment_address/paymentaddress")
    suspend fun addPaymentAddress(
        @Body updateAddressRequest: UpdateAddressRequest
    ): Response<BaseResponse>

    @POST("index.php?route=rest/guest_shipping/guestshipping")
    suspend fun addGuestShippingAddress(
        @Body updateAddressRequest: UpdateAddressRequest
    ): Response<BaseResponse>

    @GET("index.php?route=feed/rest_api/getLocations")
    suspend fun getStoreList(): Response<StoreListResponse>

    @GET("index.php?route=feed/rest_api/banners&name=Homepage")
    suspend fun getHomeBanner(): Response<HomeBannerResponse>


}

