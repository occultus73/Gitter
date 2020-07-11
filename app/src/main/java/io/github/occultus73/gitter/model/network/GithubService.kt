package com.historymakers.gitter.data.network

import androidx.lifecycle.LiveData
import com.historymakers.gitter.data.response.ReposItemResponse
import com.historymakers.gitter.data.response.UserResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("users/{username}")
    fun getUserAsync(@Path("username") type:String): Deferred<UserResponse>

    @GET("users/{username}/repos")
    fun getReposByUser(@Path("username") type: String):Deferred<List<ReposItemResponse>>
}