package com.puzzling.aoslaboratory.presentation.register

import android.content.Intent
import android.os.Bundle
import com.puzzling.aoslaboratory.R
import com.puzzling.aoslaboratory.base.BaseActivity
import com.puzzling.aoslaboratory.databinding.ActivityExampleHomeBinding

class ExampleHomeActivity :
    BaseActivity<ActivityExampleHomeBinding>(R.layout.activity_example_home) {
    private val codeCopyDialog: CodeCopyDialogFragment by lazy { CodeCopyDialogFragment() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickRegisterBtn()
        clickInputBtn()
    }

    private fun clickRegisterBtn() {
        binding.btnHomeRegister.setOnClickListener {
            codeCopyDialog.show(supportFragmentManager, "LOADING_DIALOG")
        }
    }

    private fun clickInputBtn() {
        binding.btnHomeInput.setOnClickListener {
            Intent(this, InputInviteCodeActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}
