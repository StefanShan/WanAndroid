package com.byte_stefan.login

/**
 *
 * @author chenshan
 * @date 2021.02.13
 * @since
 */

//登录成功返回数据类型
data class LoginResponse(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val coinCount: Int,
    val collectIds: List<Any>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)