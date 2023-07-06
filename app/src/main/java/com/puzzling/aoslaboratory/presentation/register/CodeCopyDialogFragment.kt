package com.puzzling.aoslaboratory.presentation.register

import android.content.ClipData
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.puzzling.aoslaboratory.databinding.FragmentCodeCopyDialogBinding
import com.puzzling.aoslaboratory.util.getClipboardManager

class CodeCopyDialogFragment :
    DialogFragment() {
    private val viewModel: CodeCopyViewModel by viewModels()
    private lateinit var clip: ClipData
    private var _binding: FragmentCodeCopyDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCodeCopyDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        clickCopyBtn()
        viewModel.codeText.value = binding.tvDialogCode.text.toString()
        clickCloseBtn()
    }

    private fun clickCloseBtn() {
        binding.btnDialogClose.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun copyText() {
        val clipboard = requireContext().getClipboardManager()
        // 일반 텍스트 복사
        clip =
            ClipData.newPlainText("CLIPBOARD_TEXT", viewModel.codeText.value.toString())
        Log.d("hoho", "Copied data: ${viewModel.codeText.value}")
        // ClipData 개체를 클립보드에 넣음
        clipboard.setPrimaryClip(clip)
    }

    private fun clickCopyBtn() {
        binding.btnDialogTop.setOnClickListener {
            copyText()
            Toast.makeText(context, "복사했습니다", Toast.LENGTH_SHORT).show()
            Log.d("hoho", "clip:: $clip")
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
