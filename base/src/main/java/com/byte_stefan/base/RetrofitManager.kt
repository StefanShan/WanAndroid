package com.byte_stefan.base

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 *
 * @author chenshan
 * @date 2021.02.13
 * @since
 */

object RetrofitService{
    private val retrofit by lazy {

        val client = OkHttpClient().newBuilder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .cookieJar(object : CookieJar{
                override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
                    if (url.url().path == "/user/login"){
                        val data = HashMap<String, String>()
                        data["cookie"] = Gson().toJson(cookies)
                        StoreFactory.write2SharePreferences(data = data)
                    }
                }

                override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
                    val cookies = StoreFactory.read2Sharepreferences(key = "cookie")
                    if (!cookies.isNullOrEmpty()){
                        return Gson().fromJson(cookies, object :TypeToken<MutableList<Cookie>>(){}.type)
                    }
                    return mutableListOf()
                }
            })
            .build()

        Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> create(service: Class<T>): T{
        return retrofit.create(service)
    }
}

suspend fun <T> Call<T>.await(): T {
    return suspendCoroutine<T> {
        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                it.resumeWithException(Throwable(t.toString()))
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful){
                    it.resume(response.body()!!)
                }else{
                    it.resumeWithException(Throwable(response.message()))
                }
            }
        })
    }
}