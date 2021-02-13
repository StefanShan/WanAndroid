package com.byte_stefan.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.byte_stefan.base.BaseResponse
import com.byte_stefan.base.RetrofitService
import com.byte_stefan.base.await
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *
 * @author chenshan
 * @date 2021.02.13
 * @since
 */
class LoginViewModel : ViewModel() {
    private val liveData : MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()

    fun login(userName:String, password:String): LiveData<BaseResponse<LoginResponse>>{
        CoroutineScope(Dispatchers.Main).launch(CoroutineExceptionHandler { _, throwable ->
            Log.e("Chenshan", "登录异常 = $throwable")
            liveData.value = BaseResponse(errorCode = -10102, errorMsg = "登录失败")
        }){
            liveData.value = RetrofitService.create(ILoginApi::class.java).login(userName, password).await()
        }
        return liveData
    }

    fun register(userName:String, password:String, repassword: String):LiveData<BaseResponse<LoginResponse>>{
        CoroutineScope(Dispatchers.Main).launch(CoroutineExceptionHandler{_, throwable ->
            Log.e("Chenshan", "注册异常 = $throwable")
            liveData.value = BaseResponse(errorCode = -10103, errorMsg = "注册失败")
        }){
            RetrofitService.create(ILoginApi::class.java).register(userName, password, repassword).await().let {
                if (it.errorCode != 0){
                    liveData.value = it
                }else{
                    //注册成功，直接登录
                    liveData.value = RetrofitService.create(ILoginApi::class.java).login(userName, password).await()
                }
            }
        }
        return liveData
    }
}