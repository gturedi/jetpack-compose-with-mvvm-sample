package com.gturedi.github.network.repository

import com.gturedi.github.network.GithubApi
import com.gturedi.github.network.model.Resource
import com.gturedi.github.util.logI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.inject

class GithubRepository : BaseRepository() {

    private val api: GithubApi by inject()

    suspend fun searchUser(q: String?) = flow {
        emit(Resource.Loading)
        val result = api.searchUser(q)
        emit(Resource.Success(result))
    }
        .flowOn(Dispatchers.IO)
        .catch {
            logI("err $it")
            emit(evaluateError(it))
        }

    suspend fun getUser(user: String?) = flow {
        emit(Resource.Loading)
        val result = api.getUser(user)
        emit(Resource.Success(result))
    }
        .flowOn(Dispatchers.IO)
        .catch {
            logI("err $it")
            emit(evaluateError(it))
        }
}