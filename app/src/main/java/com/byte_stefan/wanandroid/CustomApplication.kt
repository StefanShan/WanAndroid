package com.byte_stefan.wanandroid

import android.app.Application
import com.byte_stefan.base.StoreFactory

/**
 *
 * @author chenshan
 * @date 2021.02.13
 * @since
 */
class CustomApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        StoreFactory.init(this)
    }
}