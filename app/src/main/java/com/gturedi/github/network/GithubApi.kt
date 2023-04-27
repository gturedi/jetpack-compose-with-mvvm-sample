package com.gturedi.github.network

import com.gturedi.github.network.model.SearchUserResponse
import com.gturedi.github.network.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("/search/users")
    suspend fun searchUser(
        @Query("q") q: String?,
    ): SearchUserResponse

    @GET("/users/{user}")
    suspend fun getUser(
        @Path("user") user: String?,
    ): UserResponse
}