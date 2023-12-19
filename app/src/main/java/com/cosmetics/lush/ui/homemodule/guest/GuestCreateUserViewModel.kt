package com.cosmetics.lush.ui.homemodule.guest

import android.content.Context
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.Completed
import com.cosmetics.core.utils.Event
import com.cosmetics.core.utils.isEmail
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.interaction.profile.CreateGuestUserUseCase
import com.cosmetics.domain.interaction.profile.address.EditAddressUseCase
import com.cosmetics.domain.interaction.profile.address.GetZoneIdUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.address.Zone
import com.cosmetics.domain.model.home.guest.CreateGuestUserRequest
import com.cosmetics.domain.model.home.profile.GUEST_SHIPPING
import com.cosmetics.domain.model.home.profile.UpdateAddressRequest
import com.cosmetics.lush.BR
import com.cosmetics.lush.R
import com.cosmetics.lush.ui.homemodule.edit_address.KUWAIT_COUNTY_ID
import com.cosmetics.lush.ui.homemodule.edit_address.model.Governorate
import com.cosmetics.lush.ui.homemodule.edit_address.model.GovernorateAreaModel
import com.cosmetics.lush.utils.ObservableViewModel
import com.google.gson.Gson

class GuestCreateUserViewModel(
    private val context: Context,
    private val createGuestUserUseCase: CreateGuestUserUseCase,
    private val editAddressUseCase: EditAddressUseCase,
    private val getZoneIdUseCase: GetZoneIdUseCase,
    private val isShippingAddressNeeded: Boolean
) : ObservableViewModel() {
    var area = ""
    override fun getBindingId(): Int = BR.viewModel
    var isDeliveryBillingAddressSame = MutableLiveData(false)
    var firstName = MutableLiveData<String>("")
    var lastName = MutableLiveData<String>("")
    var email = MutableLiveData("")
    var mobile = MutableLiveData("")
    var countryName = MutableLiveData("Kuwait")
    var blockNo = MutableLiveData("")
    var street = MutableLiveData("")
    var houseNo = MutableLiveData("")
    var avenueNo = MutableLiveData("")
    var floorNo = MutableLiveData("")
    var flatNo = MutableLiveData("")
    var areas = MutableLiveData<Array<String>>()
    var _areas = MutableLiveData<Array<String>>()
    var countryID = KUWAIT_COUNTY_ID
    var zones = MutableLiveData<Array<String>>()
    var zoneList = listOf<Zone>()
    var addressId = ""
    var isSuccess = MutableLiveData<Event<BaseResponse>>()
    var isZoneHintText = MutableLiveData(true)
    var error = MutableLiveData<String>()
    var currentZoneId = -1
    var navigateToPaymentSelection = MutableLiveData<Boolean>(false)
    var isShippingInfoNeeded = MutableLiveData<Boolean>(isShippingAddressNeeded)

    private lateinit var governorateList: List<Governorate>

    @JvmField
    var areaSelectedItemPosition = 0

    @JvmField
    var zoneSelectedItemPosition = 0

    init {
        getZoneId()
    }


    @Bindable
    fun getZoneSelectedItemPosition(): Int {
        return zoneSelectedItemPosition
    }

    fun setZoneSelectedItemPosition(selectedItemPosition: Int) {
        if (selectedItemPosition >= 0) {
            zoneSelectedItemPosition = selectedItemPosition
            updateAreaListList(selectedItemPosition)
            notifyPropertyChanged(BR.zoneSelectedItemPosition)
        }
    }

    fun updateAreaListList(selectedItemPosition: Int) {
        _areas.value = governorateList
            .filter { zoneList[selectedItemPosition].name.contains(it.title) }[0].areaList
            .map { it.title }
            .toTypedArray()
        //   areaSelectedItemPosition = 0
    }

    @Bindable
    fun getAreaSelectedItemPosition(): Int {
        return areaSelectedItemPosition
    }


    fun setAreaSelectedItemPosition(selectedItemPosition: Int) {
        if (selectedItemPosition >= 0) {
            areaSelectedItemPosition = selectedItemPosition
            notifyPropertyChanged(BR._all)
        }
    }

    fun setGovernateAndArea(jsonHomeContent: String) {
        val gson = Gson()
        val model = gson.fromJson(jsonHomeContent, GovernorateAreaModel::class.java)
        governorateList = model.governorateList
    }


    fun submitAddress() {
        if (isRequestValid()) {
            executeUseCase {
                createGuestUserUseCase(
                    CreateGuestUserRequest(
                        address1 = blockNo.value!!,
                        address2 = street.value,
                        firstname = firstName.value!!,
                        lastname = lastName.value!!,
                        company = getSelectedArea(),
                        zoneId = getZoneID(),
                        countryId = countryID,
                        email = email.value!!,
                        telephone = mobile.value!!,
                        city = houseNo.value!!,
                        avenue = avenueNo.value!!,
                        floor = floorNo.value!!,
                        flat = flatNo.value!!
                    )
                )
                    .onSuccess {
                        if (isSameDeliveryShippingAddress()) {
                            submitShipppingAddress()
                        } else {
                            viewState.value = Completed
                            val response = Results.Success(it).data
                            if (!isShippingAddressNeeded) {
                                navigateToPaymentSelection.value = true
                            } else {
                                isSuccess.value = Event(response)
                            }
                        }
                    }
                    .onFailure { handleError(it) }
            }
        }
    }

    fun isSameDeliveryShippingAddress() = isDeliveryBillingAddressSame.value != null
            && isDeliveryBillingAddressSame.value!!

    fun submitShipppingAddress() {
        executeUseCase {
            editAddressUseCase(
                UpdateAddressRequest(
                    address1 = blockNo.value!!,
                    address2 = street.value,
                    firstname = firstName.value!!,
                    lastname = lastName.value!!,
                    company = getSelectedArea(),
                    zoneId = getZoneID(),
                    city = houseNo.value!!,
                    addressId = addressId,
                    type = GUEST_SHIPPING,
                    countryId = countryID,
                    avenue = avenueNo.value!!,
                    floor = floorNo.value!!,
                    flat = flatNo.value!!
                )
            )
                .onSuccess {
                    viewState.value = Completed
                    val response = Results.Success(it).data
                    isSuccess.value = Event(response)
                }
                .onFailure { handleError(it) }
        }
    }

    private fun getZoneID(): Int? {
        if (zoneList.isNullOrEmpty()) {
            return null
        }
        return zoneSelectedItemPosition.let { zoneList[it].zoneId }
    }

    private fun getSelectedArea(): String? {
        return areas.value?.let { it[areaSelectedItemPosition] }
    }

    /**
     * This API call wasn't needed. I am calling this API because the freaking backend guy is too lazy to do his job.
     */
    fun getZoneId() {
        executeUseCase {
            getZoneIdUseCase(KUWAIT_COUNTY_ID.toString())
                .onSuccess {
                    viewState.value = Completed
                    val response = Results.Success(it).data.data
                    if (response != null) {
                        zoneList = response.zone
                        countryName.value = response.name
                        countryID = response.countryId
                        if (!zoneList.isNullOrEmpty()) {
                            isZoneHintText.value = false
                            val zone: MutableList<String> =
                                zoneList.map { it.name } as MutableList<String>
                            zones.value = zone.toTypedArray()
                            if (currentZoneId >= 0) setCurrentZoneId(zoneList)
                            if (!area.isNullOrEmpty()) setCurrentArea(area)
                        }
                    }
                }
                .onFailure {
                    handleError(it) {
                        getZoneId()
                    }
                }
        }
    }

    private fun setCurrentZoneId(zone: List<Zone>) {
        zone.forEachIndexed { index, element ->
            if (element.zoneId == currentZoneId) {
                setZoneSelectedItemPosition(index)
            }
        }
    }

    private fun setCurrentArea(area: String) {
        updateAreaListList(zoneSelectedItemPosition)
        //_areas.value = governorateList[zoneSelectedItemPosition].areaList.map { it.title }.toTypedArray()
        areas.value?.forEachIndexed { index, element ->
            if (element == area) {
                setAreaSelectedItemPosition(index)
            }
        }
    }


    private fun isRequestValid(): Boolean = when {
        firstName.value.isNullOrEmpty() -> {
            handleError(context.getString(R.string.error_first_name_field_empty))
            false
        }
        lastName.value.isNullOrEmpty() -> {
            handleError(context.getString(R.string.error_last_name_field_empty))
            false
        }
        email.value.isNullOrEmpty() -> {
            handleError(context.getString(R.string.error_email_field_empty))
            false
        }
        !email.value?.isEmail()!! -> {
            handleError(context.getString(R.string.email_error))
            false
        }
        mobile.value.isNullOrEmpty() -> {
            handleError(context.getString(R.string.mobile_error))
            false
        }
        blockNo.value.isNullOrEmpty() -> {
            handleError(context.getString(R.string.error_block_no_field_empty))
            false
        }
        zoneSelectedItemPosition < 0 -> {
            handleError(context.getString(R.string.error_governorates_field_empty))
            false
        }
        firstName.value.isNullOrEmpty() -> {
            handleError(context.getString(R.string.error_name_field_empty))
            false
        }
        street.value.isNullOrEmpty() -> {
            handleError(context.getString(R.string.error_street_field_empty))
            false
        }
        else -> {
            true
        }
    }

}