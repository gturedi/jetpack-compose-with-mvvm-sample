package com.gturedi.github.di

import com.gturedi.github.network.repository.GithubRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { GithubRepository() }
}