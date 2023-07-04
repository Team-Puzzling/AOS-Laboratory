package com.puzzling.aoslaboratory.presentation.main

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class KakaoLoginManager(private val context: Context) {
    private val TAG = "kakao login"
    private lateinit var kakaoLoginState: KaKaoLoginState
    private lateinit var kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit

    private fun startKakaoLogin() {
        kakaoLoginState = getKaKaoLoginState()
        kakaoLoginCallback = getLoginCallback()

        when (kakaoLoginState) {
            KaKaoLoginState.KAKAO_TALK_LOGIN -> loginKakaoAccount()
            KaKaoLoginState.KAKAO_ACCOUNT_LOGIN -> loginKakaoAccount()
        }
    }

    // 카카오톡 설치 여부 확인
    private fun getKaKaoLoginState(): KaKaoLoginState =
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            KaKaoLoginState.KAKAO_TALK_LOGIN
        } else {
            KaKaoLoginState.KAKAO_ACCOUNT_LOGIN
        }

    // 카카오톡 미설치시 카카오 계정으로 로그인
    private fun loginKakaoAccount() {
        UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoLoginCallback)
    }

    // 카카오톡으로 로그인인
    private fun loginKakao() {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오톡으로 로그인 실패", error)

                // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }

                loginKakaoAccount()
            } else if (token != null) {
                // 토큰 저장하기
                Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
            }
        }
    }

    private fun getLoginCallback(): (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            when {
                error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                    Log.e(TAG, "접근이 거부 됨(동의 취소)")
                }
                error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                    Log.e(TAG, "유효하지 않은 앱")
                }
                error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                    Log.e(TAG, "인증 수단이 유효하지 않아 인증할 수 없는 상태")
                }
                error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                    Log.e(TAG, "요청 파라미터 오류")
                }
                error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                    Log.e(TAG, "유효하지 않은 scope ID")
                }
                error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                    Log.e(TAG, "설정이 올바르지 않음(android key hash)")
                }
                error.toString() == AuthErrorCause.ServerError.toString() -> {
                    Log.e(TAG, "서버 내부 에러")
                }
                error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                    Log.e(TAG, "앱이 요청 권한이 없음")
                }
                else -> { // Unknown
                    Log.e(TAG, "기타 에러")
                }
            }
        } else if (token != null) {
            Log.d(TAG, "로그인 성공")
            // 여기에 토큰 저장하기
        }
    }

    private fun kakaoLogout() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            } else {
                Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
    }

    // 탈퇴퇴
    private fun kakaoDeleteAccount() {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e(TAG, "연결 끊기 실패", error)
            } else {
                Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }
}

enum class KaKaoLoginState {
    KAKAO_TALK_LOGIN, KAKAO_ACCOUNT_LOGIN
}
