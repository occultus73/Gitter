package io.github.occultus73.gitter.model.data


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    val followers: Int?,
    val following: Int?,
    val id: Int,
    val login: String,
    @SerializedName("node_id")
    val nodeId: String?,
    @SerializedName("public_gists")
    val publicGists: Int?,
    @SerializedName("public_repos")
    val publicRepos: Int?,
    @SerializedName("repos_url")
    val reposUrl: String?,
    val url: String?
)