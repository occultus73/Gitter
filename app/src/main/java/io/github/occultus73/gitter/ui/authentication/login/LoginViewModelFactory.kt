package io.github.occultus73.gitter.ui.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.occultus73.gitter.network.FirebaseHelper
import io.github.occultus73.gitter.model.AuthRepository

class LoginViewModelFactory(private val firebaseHelper: FirebaseHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AuthRepository::class.java)
            .newInstance(
                AuthRepository(
                    firebaseHelper
                )
            )
    }
}