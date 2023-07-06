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
import com.puzzling.aoslaboratory.databinding.ActivityLogoutBinding
import kotlinx.coroutines.launch

class LogoutActivity : BaseActivity<ActivityLogoutBinding>(R.layout.activity_logout) {

    private val viewModel: LogoutViewModel by viewModels { ViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kakaoLogout()
        kakaoDeleteAccount()
        isLogoutSuccess()
    }

    private fun kakaoLogout() {
        binding.btnLogoutLogoutKakao.setOnClickListener {
            viewModel.kakaoLogout()
        }
    }

    private fun kakaoDeleteAccount() {
        binding.btnLogoutDeleteAccountKakao.setOnClickListener {
            viewModel.kakaoDeleteAccount()
        }
    }

    private fun isLogoutSuccess() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.isKakaoLogout.collect { isLoginSuccess ->
                    if (isLoginSuccess) {
                        val intent = Intent(this@LogoutActivity, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}
