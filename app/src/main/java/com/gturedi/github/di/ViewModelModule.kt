package com.gturedi.github.di

import com.gturedi.github.ui.MainViewModel
import com.gturedi.github.ui.search.SearchViewModel
import com.gturedi.github.ui.user.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchViewModel() }
    viewModel { UserViewModel() }
    single { MainViewModel() }
}