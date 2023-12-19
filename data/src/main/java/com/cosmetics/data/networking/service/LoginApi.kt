package com.cosmetics.data.networking.service

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.login.forgotpassword.ForgotPasswordRequest
import com.cosmetics.domain.model.login.register.request.RegistrationRequest
import com.cosmetics.domain.model.login.register.response.RegistrationResponse
import com.cosmetics.domain.model.login.token.GetTokenResponse
import com.cosmetics.domain.model.login.userlogin.LoginRequest
import com.cosmetics.domain.model.login.userlogin.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GetTokenService {
    @POST("index.php?route=feed/rest_api/gettoken&grant_type=client_credentials")
    suspend fun getToken(): Response<GetTokenResponse>
}

interface ForgotPasswordService {
    @POST("index.php?route=rest/forgotten/forgotten")
    suspend fun handleForgotPassword(@Body forgotPasswordRequest: ForgotPasswordRequest)
            : Response<BaseResponse>
}


interface LoginApi {

    @POST("index.php?route=rest/login/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("index.php?route=rest/register/register")
    suspend fun handleRegister(@Body registrationRequest: RegistrationRequest): Response<RegistrationResponse>

    @POST("index.php?route=rest/logout/logout")
    suspend fun handleLogout(): Response<BaseResponse>


}