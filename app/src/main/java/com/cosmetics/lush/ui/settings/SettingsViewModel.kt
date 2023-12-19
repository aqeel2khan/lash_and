package com.cosmetics.lush.ui.settings

import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.interaction.account.logout.GetLogoutUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.lush.BR

class SettingsViewModel(private val getLogoutUseCase: GetLogoutUseCase) : BaseViewModel() {

    var logoutResponse: MutableLiveData<BaseResponse> = MutableLiveData()
    override fun getBindingId(): Int = BR.viewModel

    fun logout() {
        executeUseCase {
            getLogoutUseCase("")
                .onSuccess {
                    viewState.value = Completed
                    val response = Results.Success(it).data
                    logoutResponse.value = response
                }
                .onFailure {
                    handleError(it)
                }
        }
    }
}