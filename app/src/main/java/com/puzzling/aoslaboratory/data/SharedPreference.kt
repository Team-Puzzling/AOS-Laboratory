package com.puzzling.aoslaboratory.data

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("puzzling", Context.MODE_PRIVATE)

    fun setAccessToken(input: String) {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("ACCESS_TOKEN", input)
        editor.commit()
    }

    fun getAccessToken(): String {
        return prefs.getString("ACCESS_TOKEN", "").toString()
    }
}
