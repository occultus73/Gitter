package io.github.occultus73.gitter.model.network

import androidx.lifecycle.LiveData
import io.github.occultus73.gitter.model.data.GithubRepository
import io.github.occultus73.gitter.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val githubService: GithubService by lazy { createGithubService(createRetrofitInstance()) }

    private fun createRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun createGithubService(retrofitInstance: Retrofit): GithubService {
        return retrofitInstance.create<GithubService>(GithubService::class.java)
    }

    fun getRepositories(userName: String): LiveData<List<GithubRepository>> {
        return githubService.getUserRepositories(userName)
    }

}