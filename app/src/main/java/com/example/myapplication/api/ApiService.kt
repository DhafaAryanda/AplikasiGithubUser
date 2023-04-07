package com.example.myapplication.api

import com.example.myapplication.database.DetailUserResponse
import com.example.myapplication.database.SearchUserResponse
import com.example.myapplication.database.UserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    @Headers("Authorization: token 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")
    fun getUser(): Call<List<UserItem>>

    @GET("users/{username}")
    fun detailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<UserItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<UserItem>>

    @GET("search/users")
    fun searchUser(@Query("q") username: String): Call<SearchUserResponse>
}