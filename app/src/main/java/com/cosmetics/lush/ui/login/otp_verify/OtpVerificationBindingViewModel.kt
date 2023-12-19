package com.cosmetics.lush.ui.login.otp_verify

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Event
import com.cosmetics.lush.BR
import com.cosmetics.lush.R

data class OtpValidationModel(var otp: String = "")
class OtpVerificationBindingViewModel : BaseViewModel() {

    val otpValidationModel = OtpValidationModel()

    val otpErrorLiveData = MutableLiveData<Int>()

    val successLiveData = MutableLiveData<Event<Boolean>>()

    override fun getBindingId(): Int = BR.viewModel

    fun verifyOtp() {
        when {
            TextUtils.isEmpty(otpValidationModel.otp) -> otpErrorLiveData.postValue(R.string.otp_empty_error)
            otpValidationModel.otp.length < 6 -> otpErrorLiveData.postValue(R.string.otp_length_error)
            else -> {
                otpErrorLiveData.postValue(0)
                successLiveData.postValue(Event(true))
            }
        }
    }
}
