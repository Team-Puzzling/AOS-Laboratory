package com.puzzling.aoslaboratory.util

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.puzzling.aoslaboratory.BuildConfig.NATIVE_APP_KEY
import com.puzzling.aoslaboratory.data.SharedPreferences

class PuzzlingApplication : Application() {

    companion object {
        lateinit var sharedPreferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, NATIVE_APP_KEY)
        sharedPreferences.init(this)
    }
}
