package com.gturedi.github.network.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserResponse(
    val id: String? = "",
    @SerializedName("login")
    val userName: String? = "",
    @SerializedName("html_url")
    val url: String? = "",
    @SerializedName("avatar_url")
    val avatarUrl: String? = "",
    @SerializedName("name")
    val fullName: String? = "",
    val location: String? = "",
    val company: String? = "",
    val blog: String? = "",
    val followers: Int? = 0,
    val following: Int? = 0,
) {
    companion object {
        fun createMock() = UserResponse(
            fullName = "gokhan turedi",
            userName = "gturedi",
            blog = "https://gturedi.com",
            location = "Istanbul, Turkey",
            avatarUrl = "https://avatars.githubusercontent.com/u/3161834?v=4",
            url = "https://github.com/gturedi",
        )
    }
}