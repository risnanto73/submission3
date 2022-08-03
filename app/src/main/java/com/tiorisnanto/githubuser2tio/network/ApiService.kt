package com.tiorisnanto.githubuser2tio.network

import com.tiorisnanto.githubuser2tio.model.ResponseSearch
import com.tiorisnanto.githubuser2tio.model.ResponseUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(
        @Query("q") q: String
    ): Call<ResponseSearch>


    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<ResponseUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollower(
        @Path("username") username: String
    ): Call<ArrayList<ResponseUser>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<ResponseUser>>
}