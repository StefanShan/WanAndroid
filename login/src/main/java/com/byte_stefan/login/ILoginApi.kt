package com.byte_stefan.login

import com.byte_stefan.base.BaseResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 *
 * @author chenshan
 * @date 2021.02.13
 * @since
 */

interface ILoginApi {

    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("username") userName: String,
        @Field("password") password: String
    ): Call<BaseResponse<LoginResponse>>

    @FormUrlEncoded
    @POST("user/register")
    fun register(
        @Field("username") userName: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): Call<BaseResponse<LoginResponse>>
}