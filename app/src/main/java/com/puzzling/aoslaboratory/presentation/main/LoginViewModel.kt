package com.puzzling.aoslaboratory.presentation.main

import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.puzzling.aoslaboratory.data.source.local.SharedPreferences
import com.puzzling.aoslaboratory.util.KakaoLoginCallback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel() : ViewModel() {
    private val _isKakaoLogin = MutableStateFlow(false)
    val isKakaoLogin = _isKakaoLogin.asStateFlow()

    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        KakaoLoginCallback {
            _isKakaoLogin.value = true
            SharedPreferences.setAccessToken("$token")
        }.handleResult(token, error)
    }
}
