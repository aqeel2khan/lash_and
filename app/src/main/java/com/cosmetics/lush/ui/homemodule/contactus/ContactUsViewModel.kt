package com.cosmetics.lush.ui.homemodule.contactus

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.domain.interaction.contactus.SubmitContactUsUseCase
import com.cosmetics.domain.model.home.contactus.ContactRequest
import com.cosmetics.lush.BR
import com.cosmetics.lush.R

class ContactUsViewModel(
    private val submitContactUsUseCase: SubmitContactUsUseCase,
    private val context: Context
) :
    BaseViewModel() {
    var description: String = ""
    var email: String = ""
    var name: String = ""
    val successLiveData = MutableLiveData<BaseResponse>()

    init {
        name = PreferenceDelegate.firstName + " " + PreferenceDelegate.lastName
        email = PreferenceDelegate.email
    }

    override fun getBindingId(): Int = BR.viewModel

    fun submit() {
        when {
            name.isNullOrEmpty() -> {
                handleError(context.getString(R.string.error_name_field_empty))
            }
            email.isNullOrEmpty() -> {
                handleError(context.getString(R.string.error_email_field_empty))
            }
            description.isNullOrEmpty() -> {
                handleError(context.getString(R.string.error_enquiry_field_empty))
            }
            else -> {
                callApi(ContactRequest(name, email, description))
            }
        }
    }

    private fun callApi(contactRequest: ContactRequest) {
        executeUseCase {
            submitContactUsUseCase(contactRequest)
                .onSuccess {
                    viewState.value = Completed
                    successLiveData.value = it
                }
                .onFailure {
                    handleError(it)
                }
        }
    }
}
