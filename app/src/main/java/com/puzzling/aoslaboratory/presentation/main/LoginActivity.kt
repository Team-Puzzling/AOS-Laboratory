package com.puzzling.aoslaboratory.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.puzzling.aoslaboratory.R
import com.puzzling.aoslaboratory.base.BaseActivity
import com.puzzling.aoslaboratory.data.ViewModelFactory
import com.puzzling.aoslaboratory.data.service.KakaoLoginService
import com.puzzling.aoslaboratory.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val kakaoLoginService = KakaoLoginService(this)
    private val viewModel: LoginViewModel by viewModels { ViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKakaoLogin()
        isKakaoLoginSuccess()
    }

    private fun startKakaoLogin() {
        binding.btnLoginKakao.setOnClickListener {
            kakaoLoginService.startKakaoLogin(viewModel.kakaoLoginCallback)
        }
    }

    private fun isKakaoLoginSuccess() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.isKakaoLogin.collect { isLoginSuccess ->
                    if (isLoginSuccess) {
                        val intent = Intent(this@LoginActivity, LogoutActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                }
            }

            // `lifecycle` is DESTROYED when the coroutine resumes. repeatOnLifecycle
            // suspends the execution of the coroutine until the lifecycle is DESTROYED.
        }
    }
}
