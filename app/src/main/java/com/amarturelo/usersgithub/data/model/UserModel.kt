package com.amarturelo.usersgithub.data.model

import com.google.gson.annotations.SerializedName
import com.amarturelo.usersgithub.domain.entity.UserEntity

data class UserModel(
    @SerializedName("avatar_url")
    val avatarUrl: String = "",
    @SerializedName("events_url")
    val eventsUrl: String = "",
    @SerializedName("followers_url")
    val followersUrl: String = "",
    @SerializedName("following_url")
    val followingUrl: String = "",
    @SerializedName("gists_url")
    val gistsUrl: String = "",
    @SerializedName("gravatar_id")
    val gravatarId: String = "",
    @SerializedName("html_url")
    val htmlUrl: String = "",
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("login")
    val login: String = "",
    @SerializedName("node_id")
    val nodeId: String = "",
    @SerializedName("organizations_url")
    val organizationsUrl: String = "",
    @SerializedName("received_events_url")
    val receivedEventsUrl: String = "",
    @SerializedName("repos_url")
    val reposUrl: String = "",
    @SerializedName("site_admin")
    val siteAdmin: Boolean = false,
    @SerializedName("starred_url")
    val starredUrl: String = "",
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("url")
    val url: String = ""
)

fun UserModel.toEntity(): UserEntity {
    return UserEntity(avatarUrl, id, login)
}