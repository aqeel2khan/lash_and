package com.cosmetics.domain.repository.home

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.address.CountryListResponse
import com.cosmetics.domain.model.home.address.ZoneListResponse
import com.cosmetics.domain.model.home.guest.CreateGuestUserRequest
import com.cosmetics.domain.model.home.profile.*
import com.cosmetics.domain.model.home.store.StoreListResponse
import com.cosmetics.domain.model.order.MyOrderDetailsResponse
import com.cosmetics.domain.model.order.OrderHistoryResponse
import com.cosmetics.domain.model.order.OrderStatusResponse

interface UserProfileRepository {
    suspend fun createGuestUser(createGuestUserRequest: CreateGuestUserRequest): Results<BaseResponse>
    suspend fun submit(updateUserProfileRequest: UpdateUserProfileRequest): Results<BaseResponse>
    suspend fun getUserProfile(): Results<UserProfileResponse>
    suspend fun getOrderHistory(pageNumber: Int): Results<OrderHistoryResponse>
    suspend fun getOrderDetails(id: String): Results<MyOrderDetailsResponse>
    suspend fun getOrderStatuses(): Results<OrderStatusResponse>
    suspend fun getAddress(): Results<AddressResponse>
    suspend fun getCountryList(): Results<CountryListResponse>
    suspend fun getZoneList(id: String): Results<ZoneListResponse>
    suspend fun editAddress(updateAddressRequest: UpdateAddressRequest): Results<BaseResponse>
    suspend fun getDeliveryAddress(isDeliverAddressSelection: Boolean): Results<AddressResponse>
    suspend fun editDeliveryAddress(setCartAddressRequest: SetCartAddressRequest): Results<BaseResponse>
}

interface StoreRepository {
    suspend fun getStoreList(): Results<StoreListResponse>
}