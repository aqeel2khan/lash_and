package com.cosmetics.lush.ui.homemodule.edit_address

import android.content.Context
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.Completed
import com.cosmetics.core.utils.Event
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.interaction.profile.address.EditAddressUseCase
import com.cosmetics.domain.interaction.profile.address.GetCountryUseCase
import com.cosmetics.domain.interaction.profile.address.GetZoneIdUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.address.Zone
import com.cosmetics.domain.model.home.profile.*
import com.cosmetics.lush.BR
import com.cosmetics.lush.R
import com.cosmetics.lush.ui.homemodule.edit_address.model.Governorate
import com.cosmetics.lush.ui.homemodule.edit_address.model.GovernorateAreaModel
import com.cosmetics.lush.utils.ObservableViewModel
import com.google.gson.Gson

const val KUWAIT_COUNTY_ID = 114

class AddressInputViewModel(
    private val context: Context,
    private val editAddressUseCase: EditAddressUseCase,
    private val getCountryUseCase: GetCountryUseCase,
    private val getZoneIdUseCase: GetZoneIdUseCase
) : ObservableViewModel() {
    override fun getBindingId(): Int = BR.viewModel
    private lateinit var governorateList: List<Governorate>
    var firstName = MutableLiveData<String>("")
    var lastName = MutableLiveData<String>("")
    var area = ""
    var blockNo = MutableLiveData("")
    var street = MutableLiveData("")
    var houseNo = MutableLiveData("")
    var avenueNo = MutableLiveData("")
    var floorNo = MutableLiveData("")
    var flatNo = MutableLiveData("")
    var postalCode = MutableLiveData("")
    var countryName = MutableLiveData("Kuwait")
    var countryID = KUWAIT_COUNTY_ID
    var zones = MutableLiveData<Array<String>>()
    var areas = MutableLiveData<Array<String>>()
    var _areas = MutableLiveData<Array<String>>()
    var zoneList = listOf<Zone>()
    var addressId = ""
    var addressType = 1
    var isSuccess = MutableLiveData<Event<BaseResponse>>()
    var isZoneHintText = MutableLiveData(true)
    var error = MutableLiveData<String>()
    var currentZoneId = -1

    @JvmField
    var zoneSelectedItemPosition = 0

    @JvmField
    var areaSelectedItemPosition = 0

    init {
        getZoneId()
    }

    @Bindable
    fun getZoneSelectedItemPosition(): Int {
        return zoneSelectedItemPosition
    }

    fun setZoneSelectedItemPosition(selectedItemPosition: Int) {
        if (selectedItemPosition >= 0 &&
            zoneSelectedItemPosition != selectedItemPosition
        ) {
            zoneSelectedItemPosition = selectedItemPosition
            updateAreaListList(selectedItemPosition)
            notifyPropertyChanged(BR.zoneSelectedItemPosition)
        }
    }

    fun updateAreaListList(selectedItemPosition: Int) {
        try {
            _areas.value = governorateList
                .filter { zoneList[selectedItemPosition].name.contains(it.title) }[0].areaList
                .map { it.title }
                .toTypedArray()
        } catch (e: Exception) {
        }


        //areaSelectedItemPosition = 0
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

    fun initUserData(it: Address) {
        addressId = it.addressId.toString()
        firstName.value = it.firstname
        lastName.value = it.lastname
        blockNo.value = it.address1
        street.value = it.address2
        houseNo.value = it.city
        area = it.company
        postalCode.value = it.postcode
        currentZoneId = it.zoneId
        avenueNo.value = it.avenue
        floorNo.value = it.floor
        flatNo.value = it.flat
    }

    fun submitAddress() {
        if (isRequestValid()) {
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
                        avenue = avenueNo.value,
                        floor = floorNo.value,
                        flat = flatNo.value,
                        addressId = addressId,
                        type = addressType,
                        countryId = countryID
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
                            if (!area.isNullOrEmpty())
                                setCurrentArea(area)
                            if (addressType == NORMAL_ADDRESS
                                || addressType == PAYMENT_ADDRESS ||
                                addressType == DELIVERY_ADDRESS
                            ) updateAreaListList(
                                zoneSelectedItemPosition
                            )
                            handleGuestMode()
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

    private fun handleGuestMode() {
        if (addressType == GUEST_SHIPPING && !zoneList.isNullOrEmpty()) {
            areas.value = getAreaList(governorateList, zoneList)
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
        blockNo.value.isNullOrEmpty() -> {
            handleError(context.getString(R.string.error_block_no_field_empty))
            false
        }
        zoneSelectedItemPosition < 0 -> {
            handleError(context.getString(R.string.error_governorates_field_empty))
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

private fun getAreaList(
    governorateList: List<Governorate>,
    zoneList: List<Zone>
): Array<String>? {
    return governorateList.filter { (zoneList[0].name).contains(it.title) }[0].areaList.map {
        it.title
    }.toTypedArray()
}