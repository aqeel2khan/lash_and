package com.cosmetics.data.repository.home

import com.cosmetics.data.networking.service.HomeService
import com.cosmetics.data.utils.safeApiCall
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.address.CountryListResponse
import com.cosmetics.domain.model.home.address.ZoneListResponse
import com.cosmetics.domain.model.home.guest.CreateGuestUserRequest
import com.cosmetics.domain.model.home.profile.*
import com.cosmetics.domain.model.order.MyOrderDetailsResponse
import com.cosmetics.domain.model.order.OrderHistoryResponse
import com.cosmetics.domain.model.order.OrderStatusResponse
import com.cosmetics.domain.repository.home.UserProfileRepository


class UserProfileRepositoryImpl(private val homeService: HomeService) :
    UserProfileRepository {
    override suspend fun createGuestUser(createGuestUserRequest: CreateGuestUserRequest)
            : Results<BaseResponse> = safeApiCall {
        homeService.createGuestUser(createGuestUserRequest)
    }

    override suspend fun submit(updateUserProfileRequest: UpdateUserProfileRequest)
            : Results<BaseResponse> =
        safeApiCall { homeService.updateUserProfile(updateUserProfileRequest) }

    override suspend fun getUserProfile(): Results<UserProfileResponse> =
        safeApiCall {
            homeService.getUserProfile()
        }

    override suspend fun getOrderHistory(pageNumber: Int): Results<OrderHistoryResponse> =
        safeApiCall {
            homeService.getOrderHistory(page = pageNumber)
        }

    override suspend fun getOrderDetails(id: String): Results<MyOrderDetailsResponse> =
        safeApiCall {
            homeService.getOrderDetails(id)
        }

    override suspend fun getOrderStatuses(): Results<OrderStatusResponse> =
        safeApiCall {
            homeService.getOrderStatuses()
        }

    override suspend fun getAddress(): Results<AddressResponse> =
        safeApiCall { homeService.getAddress() }

    override suspend fun getCountryList(): Results<CountryListResponse> =
        safeApiCall { homeService.getCountryList() }

    override suspend fun getZoneList(id: String): Results<ZoneListResponse> =
        safeApiCall { homeService.getZoneList(id) }

    override suspend fun editAddress(updateAddressRequest: UpdateAddressRequest)
            : Results<BaseResponse> =
        safeApiCall {
            when (updateAddressRequest.type) {
                EDIT_ADDRESS -> {
                    homeService.editAddress(updateAddressRequest.addressId, updateAddressRequest)
                }
                DELIVERY_ADDRESS -> {
                    homeService.addShippingAddress(updateAddressRequest)
                }
                PAYMENT_ADDRESS -> {
                    homeService.addPaymentAddress(updateAddressRequest)
                }
                GUEST_SHIPPING -> {
                    homeService.addGuestShippingAddress(updateAddressRequest)
                }
                else -> {
                    homeService.addAddress(updateAddressRequest)
                }
            }
        }

    override suspend fun getDeliveryAddress(isDeliverAddressSelection: Boolean): Results<AddressResponse> =
        safeApiCall {
            if (isDeliverAddressSelection)
                homeService.getPaymentAddress()
            else homeService.getDeliveryAddress()
        }

    override suspend fun editDeliveryAddress(setCartAddressRequest: SetCartAddressRequest)
            : Results<BaseResponse> =
        safeApiCall {
            if (setCartAddressRequest.isPaymentAddress)
                homeService.editPaymentAddress(setCartAddressRequest)
            else homeService.editDeliveryAddress(setCartAddressRequest)
        }

}