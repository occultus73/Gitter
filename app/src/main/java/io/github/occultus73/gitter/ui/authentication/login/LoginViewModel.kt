package io.github.occultus73.gitter.ui.authentication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.occultus73.gitter.utils.*
import io.github.occultus73.gitter.model.AuthRepository
import io.github.occultus73.gitter.utils.AuthListner
import io.github.occultus73.gitter.utils.StateResponse
import io.github.occultus73.gitter.utils.isLoginDataValid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private var _errorCode = MutableLiveData<Int>()

    lateinit var authListner : AuthListner
    val errorCode  : LiveData<Int>
          get() = _errorCode
    private var _goToSignUp = MutableLiveData<Boolean>()
    val goToSignUp : LiveData<Boolean>
          get() = _goToSignUp
    init {
        _goToSignUp.postValue(false)
    }

     fun onLoginButtonClicked() {
        var email = email.value.orEmpty()
        var password = password.value.orEmpty()
        var code = isLoginDataValid(email,password)

        if(code == -1) {
            uiScope.launch {
                loginUser(email,password)
            }
        } else {
            _errorCode.postValue(code)
        }

    }

    fun goToSignUpClicked() {
         _goToSignUp.postValue(true)
    }

    fun goToSignUpComplete() {
        _goToSignUp.postValue(false)
    }

    private suspend fun loginUser(email : String, password : String) {
        authRepository.loginUser(email, password)
            .collect { state ->
                when(state) {
                     is StateResponse.Loading -> {
                         authListner.onLoading()
                        }
                     is StateResponse.Success -> {
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

//    override fun onOkClick() {
//
//    }


}