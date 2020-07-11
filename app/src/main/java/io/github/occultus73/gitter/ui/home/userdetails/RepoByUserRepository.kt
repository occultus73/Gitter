package com.historymakers.gitter.view.home.userdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.occultus73.gitter.model.network.RetrofitClient
import java.lang.Exception

class RepoByUserRepository {
    private var _repoResponse = MutableLiveData<List<Any>>()

    suspend fun getRepoListByUser(searchUser: String) : LiveData<List<Any>> {
        val getUsersProperties = RetrofitClient.retrofitInstance.getReposByUser(searchUser)
        try {
            _repoResponse.postValue(getUsersProperties.await())

        } catch (e: Exception) {
            _repoResponse.postValue(listOf(e.localizedMessage))
        }
        return _repoResponse
    }
}