package io.github.occultus73.gitter.model

import com.google.firebase.auth.AuthResult
import io.github.occultus73.gitter.network.FirebaseHelper
import io.github.occultus73.gitter.utils.StateResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class AuthRepository(private val firebaseHelper: FirebaseHelper) {

    fun loginUser(email : String, password : String)
            = flow<StateResponse<AuthResult>> {
        emit(StateResponse.loading())
        val authResult = firebaseHelper.firebaseAuth
            .signInWithEmailAndPassword(email,password).await()
        emit(StateResponse.Success(authResult))
    }.catch {
        emit(StateResponse.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun signUpUser(email : String, password : String) =
        flow<StateResponse<AuthResult>> {
            emit(StateResponse.loading())
            val authResult = firebaseHelper.firebaseAuth
                .createUserWithEmailAndPassword(email,password).await()
            emit(StateResponse.success(authResult))

        }.catch {
            emit(StateResponse.failed(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    fun logOut()
            = flow<StateResponse<Unit>> {
        emit(StateResponse.loading())
        val logout = firebaseHelper.firebaseAuth.signOut()
        emit(StateResponse.success(logout))
    }.catch {
        emit(StateResponse.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}