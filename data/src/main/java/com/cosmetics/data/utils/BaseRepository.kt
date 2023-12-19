package com.cosmetics.data.utils

import androidx.lifecycle.MutableLiveData
import com.cosmetics.data.networking.interceptor.AuthenticatorException
import com.cosmetics.data.networking.interceptor.NoNetworkException
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.google.gson.Gson
import retrofit2.Response
import java.util.concurrent.CancellationException

var session: MutableLiveData<Boolean> = MutableLiveData(false)
const val CANCELLATION_EXCEPTION = "CancellationException"

@Suppress("ConstantConditionIf")
suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Results<T> = try {
    val response = call.invoke()
    if (response.isSuccessful && response.body() != null) {
        var baseResponse = response.body() as BaseResponse
        if (baseResponse.success == 1) {
            Results.Success(response.body()!!)
        } else {
            Results.Error(baseResponse.error[0])
        }
    } else {
        val error = response.errorBody()?.string()
        var baseResponse = Gson().fromJson(error, BaseResponse::class.java)
        Results.Error(baseResponse.error[0])
    }
} catch (e: NoNetworkException) {
    e.printStackTrace()
    Results.Error(e.message)
} catch (e: CancellationException) {
    e.printStackTrace()
    Results.Error(CANCELLATION_EXCEPTION)
} catch (e: AuthenticatorException) {
    e.printStackTrace()
    session.postValue(true)
    Results.Error(e.message)
} catch (e: IllegalStateException) {
    e.printStackTrace()
    Results.Error("")
} catch (e: Exception) {
    e.printStackTrace()
    Results.Error("Something went wrong.")
}
