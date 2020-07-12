package io.github.occultus73.gitter.ui.authentication.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.occultus73.gitter.model.AuthRepository
import io.github.occultus73.gitter.utils.AuthListner
import io.github.occultus73.gitter.utils.StateResponse
import io.github.occultus73.gitter.utils.isSignUpDataValid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegistrationViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val uiScope = CoroutineScope(Dispatchers.Main)
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var confirmPassword = MutableLiveData<String>()
    private var _errorCode = MutableLiveData<Int>()
    lateinit var authListner : AuthListner
    val errorCode  : LiveData<Int>
        get() = _errorCode
    private var _goToLogin = MutableLiveData<Boolean>()
    val goToLogin : LiveData<Boolean>
        get() = _goToLogin
    init {
        _goToLogin.postValue(false)
    }



    fun onSignUpClicked() {
        var email = email.value.orEmpty()
        var password = password.value.orEmpty()
        var confirmPassword = confirmPassword.value.orEmpty()
        var code = isSignUpDataValid(email, password, confirmPassword)
        if(code == -1) {
             uiScope.launch {
                 signUpUser(email, password)
             }
        } else {
            _errorCode.postValue(code)
        }

    }

    fun goToLoginClicked() {
        _goToLogin.postValue(true)
    }

    fun goToLoginComplete() {
        _goToLogin.postValue(false)
    }

    private suspend fun signUpUser(email : String, password : String) {
        authRepository.signUpUser(email, password)
            .collect { state ->
                when(state) {
                    is StateResponse.Loading -> {
                        authListner.onLoading()
                    }
                    is StateResponse.Success -> {
                         state.data.user?.sendEmailVerification()
                        if(state.data.user?.isEmailVerified!!) {
                            authListner.onSuccess()
                        } else {
                            authListner.onFailure("Please verify email - link sent")
                        }
                    }
                    is StateResponse.Failed -> {
                        authListner.onFailure(state.message)
                    }
                }

            }
    }

}