package com.historymakers.gitter.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.historymakers.gitter.data.db.UserDAO

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val userDAO: UserDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(userDAO) as T
    }

}