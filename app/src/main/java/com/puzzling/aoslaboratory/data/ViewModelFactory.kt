package com.puzzling.aoslaboratory.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.puzzling.aoslaboratory.data.service.KakaoLoginService
import com.puzzling.aoslaboratory.presentation.main.LoginViewModel
import com.puzzling.aoslaboratory.presentation.main.LogoutViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LogoutViewModel::class.java) -> {
                val repository = KakaoLoginService(context)
                LogoutViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel() as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel : ${modelClass.name}")
            }
        }
    }
}
