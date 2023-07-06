package com.puzzling.aoslaboratory.util

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.puzzling.aoslaboratory.BuildConfig.NATIVE_APP_KEY
import com.puzzling.aoslaboratory.data.source.local.SharedPreferences

class PuzzlingApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, NATIVE_APP_KEY)
        SharedPreferences.init(this)
    }
}
