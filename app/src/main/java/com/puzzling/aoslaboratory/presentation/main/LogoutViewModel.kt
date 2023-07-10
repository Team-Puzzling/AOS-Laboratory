package com.puzzling.aoslaboratory.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puzzling.aoslaboratory.data.service.KakaoLoginService
import com.puzzling.aoslaboratory.util.KakaoLogoutCallback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LogoutViewModel(private val kakaoLoginService: KakaoLoginService) : ViewModel() {
    private val _isKakaoLogout = MutableStateFlow(false)
    val isKakaoLogout = _isKakaoLogout.asStateFlow()

    val kakaoCallback: (Throwable?) -> Unit = { error ->
        KakaoLogoutCallback {
            _isKakaoLogout.value = true
        }.handleResult(error)
    }

    fun kakaoLogout() = viewModelScope.launch {
        kakaoLoginService.kakaoLogout(kakaoCallback)
    }

    fun kakaoDeleteAccount() = viewModelScope.launch {
        kakaoLoginService.kakaoDeleteAccount(kakaoCallback)
    }
}
