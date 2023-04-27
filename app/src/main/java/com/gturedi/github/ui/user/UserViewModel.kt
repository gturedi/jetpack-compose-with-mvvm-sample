package com.gturedi.github.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gturedi.github.network.model.Resource
import com.gturedi.github.network.model.UserResponse
import com.gturedi.github.network.repository.GithubRepository
import com.gturedi.github.util.logI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserViewModel : ViewModel(), KoinComponent {

    private val repository: GithubRepository by inject()

    private val _userResp = MutableStateFlow<Resource<UserResponse>>(Resource.Init)
    val userResp = _userResp.asStateFlow()

    fun getUser(name: String?) {
        viewModelScope.launch {
            repository.getUser(name)
                .collect {
                    logI("getUser $it")
                    _userResp.value = it
                }
        }
    }
}