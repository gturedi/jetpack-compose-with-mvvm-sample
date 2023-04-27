package com.gturedi.github.network.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SearchUserResponse(
    val items: List<UserModel>,
)

@Keep
data class UserModel(
    val id: String? = "",
    @SerializedName("login")
    val userName: String? = "",
    @SerializedName("html_url")
    val url: String? = "",
    @SerializedName("avatar_url")
    val avatarUrl: String? = "",
) {
    companion object {
        fun createMock() = UserModel(
            userName = "gturedi",
            avatarUrl = "https://avatars.githubusercontent.com/u/3161834?v=4",
            url = "https://github.com/gturedi",
        )
    }
}