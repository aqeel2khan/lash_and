package com.cosmetics.lush.ui.login.login

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.result.Result
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.core.utils.Event
import com.cosmetics.core.utils.isEmail
import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.domain.interaction.account.token.GetTokenUseCase
import com.cosmetics.domain.interaction.account.userlogin.GetLoginUseCase
import com.cosmetics.domain.model.login.userlogin.LoginRequest
import com.cosmetics.domain.model.login.userlogin.LoginResponse
import com.cosmetics.lush.BR
import com.cosmetics.lush.R

class LoginViewModel(
    private val getLoginUseCase: GetLoginUseCase,
    private val getTokenUseCase: GetTokenUseCase
) : BaseViewModel() {
    val loginValidationModel = LoginRequest()
    val emailErrorLiveData = MutableLiveData<Int>()
    val passwordErrorLiveData = MutableLiveData<Int>()
    val successLiveData = MutableLiveData<Event<Boolean>>()
    override fun getBindingId(): Int = BR.viewModel

    fun login() {
        if (!loginValidationModel.email.isEmail()) {
            emailErrorLiveData.postValue(R.string.email_error)
        } else if (TextUtils.isEmpty(loginValidationModel.password)) {
            passwordErrorLiveData.postValue(R.string.password_empty_error)
        } else {
            clearErrors()
            callLoginApi()
        }
    }

    fun tokenCheck(isProgress: Boolean = true, trigger: () -> Unit) {
        if (PreferenceDelegate.token.isEmpty()) {
            getToken(isProgress, trigger = trigger)
        } else {
            trigger()
        }
    }

    fun getToken(isProgress: Boolean, trigger: () -> Unit) {
        executeUseCase(isProgress) {
            getTokenUseCase("")
                .onSuccess {
                    viewState.value = Completed
                    val response = Result.Success(it).data
                    PreferenceDelegate.token = response.data.accessToken
                    //tokenResponse.value = action
                    trigger()
                }
                .onFailure {
                    handleError(it)
                }
        }
    }

    private fun callLoginApi() {
        executeUseCase {
            getLoginUseCase(loginValidationModel)
                .onSuccess {
                    viewState.value = Completed
                    val response = Result.Success(it).data
                    saveToPreference(response)
                    successLiveData.postValue(Event(true))
                }
                .onFailure {
                    PreferenceDelegate.token = ""
                    handleError(it)
                }
        }
    }

    private fun saveToPreference(response: LoginResponse) {
        PreferenceDelegate.firstName = response.data.firstname
        PreferenceDelegate.lastName = response.data.lastname
        PreferenceDelegate.email = response.data.email
        PreferenceDelegate.telephone = response.data.telephone
        PreferenceDelegate.customerId = response.data.customerId
        PreferenceDelegate.cartCountProducts = response.data.cartCountProducts
        PreferenceDelegate.wishlistTotal = response.data.wishlistTotal
    }

    private fun clearErrors() {
        emailErrorLiveData.postValue(0)
        passwordErrorLiveData.postValue(0)
    }
}
