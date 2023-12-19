package com.cosmetics.lush.ui.splash

import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.result.Result
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.domain.interaction.account.token.GetTokenUseCase
import com.cosmetics.domain.model.login.token.GetTokenResponse
import com.cosmetics.lush.BR


class SplashViewModel(
    private val getTokenUseCase: GetTokenUseCase
) : BaseViewModel() {
    private val tokenResponse: MutableLiveData<GetTokenResponse> = MutableLiveData()
    override fun getBindingId(): Int = BR.viewModel

    fun getToken(): MutableLiveData<GetTokenResponse> {
        executeUseCase(false) {
            getTokenUseCase("")
                .onSuccess {
                    viewState.value = Completed
                    val response = Result.Success(it).data
                    PreferenceDelegate.token = response.data.accessToken
                    tokenResponse.value = response
                }
                .onFailure {
                    handleError(it) {
                        getToken()
                    }
                }
        }
        return tokenResponse
    }
}