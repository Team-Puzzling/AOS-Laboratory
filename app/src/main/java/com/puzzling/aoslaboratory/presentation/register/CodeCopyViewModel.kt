package com.puzzling.aoslaboratory.presentation.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CodeCopyViewModel : ViewModel() {
    val codeText: MutableLiveData<String> = MutableLiveData("")
}
