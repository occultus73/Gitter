package com.historymakers.gitter.view.home.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.occultus73.gitter.model.network.RetrofitClient
import java.lang.Exception

class SearchValidUserRepository {

    private var _userResponse = MutableLiveData<Any>()

    suspend fun searchUser(searchUser: String) : LiveData<Any> {
        val getUsersProperties = RetrofitClient.retrofitInstance.getUserAsync(searchUser)
        try {
            _userResponse.value = getUsersProperties.await()

        } catch (e: Exception) {
            _userResponse.value = e.localizedMessage
        }
        return _userResponse
    }
}