package com.cosmetics.lush.ui

import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.domain.interaction.appInfo.GetAppInformationUseCase
import com.cosmetics.domain.interaction.banner.HomeBannerUseCase
import com.cosmetics.domain.interaction.categories.GetCategoryUseCase
import com.cosmetics.domain.interaction.profile.GetUserProfileUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.appinfo.AppInformation
import com.cosmetics.domain.model.home.appinfo.AppInformationResponse
import com.cosmetics.domain.model.home.request.HomeResponse
import com.cosmetics.lush.BR
import com.google.gson.Gson

data class HeaderViewUserProfile(
    var userName: String = getUserName(),
    var email: String = PreferenceDelegate.email
)

fun getUserName(): String =
    PreferenceDelegate.firstName + " " + PreferenceDelegate.lastName

class MainViewModel(
    private val getAppInformationUseCase: GetAppInformationUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    val getCategoryUseCase: GetCategoryUseCase,
    private val getHomeBannerUseCase: HomeBannerUseCase
) : BaseViewModel() {
    var userProfile = MutableLiveData<HeaderViewUserProfile>()
    var appInformation = MutableLiveData<List<AppInformation>>()
    var categoryData = MutableLiveData<HomeResponse>()
    var homeBannerData = MutableLiveData<HomeBannerData>()

    init {
        userProfile.value = HeaderViewUserProfile()
    }

    fun refreshAPIs() {
        if (userProfile.value == null) {
            getUserProfileUseCase()
        }
        // getAppInformation()
        if (homeBannerData.value == null) {
            getHomeBanner()
        }
        if (appInformation.value == null) {
            getAppInformation()
        }

    }

    fun refreshAPIonLocalChange() {
        getHomeBanner()
        getCategories()
    }

    fun getCategories() {
        var isLoadingRequired = true
        categoryData.value?.let {
            if (!it.data.isNullOrEmpty()) isLoadingRequired = false
        }
        executeUseCase(isLoadingRequired) {
            getCategoryUseCase("")
                .onSuccess {
                    viewState.value = Completed
                    val response = Results.Success(it).data
                    var validSet = response.data.filter { category ->
                        !category.categories.isNullOrEmpty()
                    }
                    response.data = validSet
                    categoryData.postValue(response)
                }
                .onFailure {
                    handleError(it) {
                        getCategories()
                        refreshAPIs()
                        getAppInformation()
                    }
                }
        }
    }

    private fun getUserProfileUseCase() {
        executeUseCase(false) {
            getUserProfileUseCase("")
                .onSuccess {
                    val response = Results.Success(it).data
                    with(response.userProfile) {
                        PreferenceDelegate.lastName = lastname
                        PreferenceDelegate.firstName = firstname
                        userProfile.value = HeaderViewUserProfile(getUserName(), email)
                    }
                }
        }
    }

    fun setUserProfileInfo() {
        userProfile.value = HeaderViewUserProfile(getUserName(), PreferenceDelegate.email)
    }

    private fun getAppInformation() {
        val gson = Gson()
        executeUseCase(false) {
            checkCachedAppInfo(gson)
            getAppInformationUseCase("")
                .onSuccess {
                    val response = Results.Success(it).data
                    appInformation.value = response.appInformation
                    try {
                        PreferenceDelegate.appInformations = gson.toJson(response)
                    } catch (e: Exception) {
                    }
                }
        }
    }

    private fun checkCachedAppInfo(gson: Gson) {
        if (!PreferenceDelegate.appInformations.isNullOrBlank()) {
            try {
                val cachedValue = gson.fromJson<AppInformationResponse>(
                    PreferenceDelegate.appInformations,
                    AppInformationResponse::class.java
                )
                appInformation.value = cachedValue.appInformation
            } catch (e: Exception) {
            }
        }
    }

    private fun getHomeBanner() {
        executeUseCase(false) {
            getHomeBannerUseCase("")
                .onSuccess {
                    with(Results.Success(it).data) {
                        if (this.data.isNotEmpty()) {
                            homeBannerData.value = HomeBannerData(
                                link = data[0].link,
                                image = data[0].image
                            )
                        }
                    }
                }
        }
    }

    override fun getBindingId(): Int = BR.viewModel
}


data class HomeBannerData(
    val image: String,
    val link: String
)