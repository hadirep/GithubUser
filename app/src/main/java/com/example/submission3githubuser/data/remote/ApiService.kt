package com.example.submission3githubuser.data.remote

import com.example.submission3githubuser.BuildConfig
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: ${BuildConfig.API_KEY}")
    fun getListAndSearch(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: ${BuildConfig.API_KEY}")
    suspend fun getDetail(
        @Path("username") username: String
    ): DetailResponse

    @GET("users/{username}/followers")
    @Headers("Authorization: ${BuildConfig.API_KEY}")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: ${BuildConfig.API_KEY}")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>
}