package com.cosmetics.lush.ui.homemodule.appinfo

import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.domain.interaction.appInfo.GetAppInformationDetailsUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.lush.BR
import org.jsoup.Jsoup

class AppInformationDetailsViewModel(
    private val id: Int,
    private val getAppInformationDetailsUseCase: GetAppInformationDetailsUseCase
) : BaseViewModel() {
    var appInformation = MutableLiveData<String>()

    init {
        getAppInformationDetails()
    }

    private fun getAppInformationDetails() {
        executeUseCase {
            getAppInformationDetailsUseCase(id)
                .onSuccess {
                    val response = Results.Success(it).data
                    appInformation.value =
                        Jsoup.parse(response.appInformationDetails.description).text()
                    viewState.value = Completed
                }
                .onFailure {
                    handleError(it) {
                        getAppInformationDetails()
                    }
                }
        }
    }

    override fun getBindingId(): Int = BR.viewModel

}
