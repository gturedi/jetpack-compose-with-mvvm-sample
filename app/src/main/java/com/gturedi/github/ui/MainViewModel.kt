package com.gturedi.github.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent

class MainViewModel : ViewModel(), KoinComponent {

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _showBack = MutableStateFlow(false)
    val showBack = _showBack.asStateFlow()

    //private val _errorAlertMsg = MutableStateFlow("")
    //val errorAlertMsg = _errorAlertMsg.asStateFlow()

    fun changeTitle(title: String?) {
        _title.value = title.orEmpty()
    }

    fun showBack(value: Boolean) {
        _showBack.value = value
    }

    /*fun showError(message: String) {
        _errorAlertMsg.value = message
    }*/
}