package com.puzzling.aoslaboratory.presentation.register

import android.content.ClipboardManager
import android.util.Log
import androidx.core.content.getSystemService
import androidx.lifecycle.LifecycleService

class ClipBoardService : LifecycleService(), ClipboardManager.OnPrimaryClipChangedListener {
    private lateinit var mManager: ClipboardManager

    override fun onCreate() {
        super.onCreate()
        mManager = getSystemService<ClipboardManager>()!!
        // Register the listener
        mManager.addPrimaryClipChangedListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the listener
        mManager.removePrimaryClipChangedListener(this)
    }

    override fun onPrimaryClipChanged() {
        val clipData = mManager.primaryClip
        if (clipData != null) {
            val dataCount = clipData.itemCount
            for (i in 0 until dataCount) {
                val item = clipData.getItemAt(i)
                Log.e("hoho", "clip data - item: ${item.coerceToText(this)}")
            }
        } else {
            Log.e("hoho", "No Manager or No Clip data")
        }
    }
}
