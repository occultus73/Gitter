package io.github.occultus73.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.occultus73.gitter.model.room.UserDAO


@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val userDAO: UserDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(userDAO) as T
    }

}