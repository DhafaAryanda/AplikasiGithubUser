package com.example.myapplication.database

import com.google.gson.annotations.SerializedName


data class UserItem(

    @field:SerializedName("following_url")
    val followingUrl: String,

    @field:SerializedName("starred_url")
    val starredUrl: String,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("followers_url")
    val followersUrl: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("id")
    val id: Int
)

data class DetailUserResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("following_url")
    val followingUrl: String? = null,

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("followers_url")
    val followersUrl: String? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("following")
    val following: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: String? = null
)


data class SearchUserResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("items")
    val items: List<UserItem>
)
