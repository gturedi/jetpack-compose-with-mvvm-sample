package com.gturedi.github.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gturedi.github.network.model.Resource
import com.gturedi.github.network.model.SearchUserResponse
import com.gturedi.github.network.repository.GithubRepository
import com.gturedi.github.util.logI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchViewModel : ViewModel(), KoinComponent {

    private val repository: GithubRepository by inject()

    private val _searchResp = MutableStateFlow<Resource<SearchUserResponse>>(Resource.Init)
    val searchResp = _searchResp.asStateFlow()

    fun search(name: String?) {
        viewModelScope.launch {
            repository.searchUser(name)
                .collect {
                    logI("search $it")
                    _searchResp.value = it
                }
        }
    }
}