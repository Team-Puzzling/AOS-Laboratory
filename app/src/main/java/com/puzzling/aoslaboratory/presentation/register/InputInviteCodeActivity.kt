package com.puzzling.aoslaboratory.presentation.register

import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.puzzling.aoslaboratory.databinding.ActivityInputInviteCodeBinding
import com.puzzling.aoslaboratory.util.getClipboardManager

class InputInviteCodeActivity :
    AppCompatActivity() {

    private lateinit var binding: ActivityInputInviteCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputInviteCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickCloseBtn()
        pastedData()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            // foreground 상태라면 호출됨
            Log.d("hoho", "49")
            Log.d("hoho!", "clipboard: " + pastedData().toString().length)
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        }
    }

    private fun pastedData() {
        val clipboard = getClipboardManager()
        var pasteData = ""
        if (!(clipboard.hasPrimaryClip())) {
            Log.d("hoho", "pasteData is empty!!!")
        } else if (!(clipboard.primaryClipDescription?.hasMimeType(MIMETYPE_TEXT_PLAIN))!!) {
            Log.d("hoho", "type이 MIMETYPE_TEXT_PLAIN 아님 !!")
        } else {
            val item = clipboard.primaryClip?.getItemAt(0)
            pasteData = item?.text.toString()
            Log.d("hoho", "pasteData: $pasteData")
            binding.edtInputCode.setText(pasteData)
        }
    }

    private fun clickCloseBtn() {
        binding.btnInputClose.setOnClickListener {
            Intent(this, ExampleHomeActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}
