package com.byte_stefan.base

data class BaseResponse<T>(
    val data: T? = null,
    val errorCode: Int,
    val errorMsg: String
)