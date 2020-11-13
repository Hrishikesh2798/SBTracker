package com.ctlb.sbtracker.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ParentHomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Parent Fragment"
    }
    val text: LiveData<String> = _text
}