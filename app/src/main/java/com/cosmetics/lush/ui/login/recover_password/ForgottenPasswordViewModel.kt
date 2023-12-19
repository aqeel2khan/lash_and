package com.cosmetics.lush.ui.login.recover_password

import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.core.utils.isEmail
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.domain.interaction.account.forgotpassword.GetForgotPasswordUseCase
import com.cosmetics.domain.model.login.forgotpassword.ForgotPasswordRequest
import com.cosmetics.lush.BR
import com.cosmetics.lush.R


class ForgottenPasswordViewModel(
    private val getForgotPasswordUseCase
    : GetForgotPasswordUseCase
) : BaseViewModel() {
    var email = ""
    val emailErrorLiveData = MutableLiveData<Int>()
    val successLiveData = MutableLiveData<BaseResponse>()

    override fun getBindingId(): Int = BR.viewModel

    fun recoverPassword() {
        if (!email.isEmail()) {
            emailErrorLiveData.postValue(R.string.email_error)
        } else {
            emailErrorLiveData.postValue(0)
            callForgotPasswordAPI()
        }
    }


    private fun callForgotPasswordAPI() {
        executeUseCase {
            getForgotPasswordUseCase(ForgotPasswordRequest(email))
                .onSuccess {
                    viewState.value = Completed
                    successLiveData.postValue(it)
                }
                .onFailure {
                    PreferenceDelegate.token = ""
                    handleError(it)
                }
        }
    }

}
