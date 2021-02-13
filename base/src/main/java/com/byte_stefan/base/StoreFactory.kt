package com.byte_stefan.base

import android.content.Context


/**
 *
 * @author chenshan
 * @date 2021.02.13
 * @since
 */
object StoreFactory {

    private var mContext: Context? = null

    fun init(ctx: Context){
        mContext = ctx
    }

    fun <T> write2SharePreferences(name: String? = "WanAndroid", data: HashMap<String, T>){
        if (mContext == null){
            throw Throwable("未初始化 StoreFactory!")
        }
        mContext!!.getSharedPreferences(name, Context.MODE_PRIVATE).edit().apply {
            for((key,value) in data){
                if (value is Collection<*>){
                    val strBuild = StringBuilder()
                    for (data in value){
                        strBuild.append(data.toString()).append("|")
                    }
                    putString(key, strBuild.toString())
                }else {
                    putString(key, value.toString())
                }
            }
        }.apply()
    }

    fun read2Sharepreferences(name: String? = "WanAndroid", key:String, defaultValue: String? = ""): String? {
        if (mContext == null){
            throw Throwable("未初始化 StoreFactory!")
        }
        return mContext!!.getSharedPreferences(name, Context.MODE_PRIVATE).getString(key, defaultValue)
    }
}