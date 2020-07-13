package io.github.occultus73.gitter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.auth.AuthResult
import io.github.occultus73.gitter.model.network.FirebaseHelper
import io.github.occultus73.gitter.ui.authentication.AuthRepository
import io.github.occultus73.gitter.ui.authentication.login.LoginViewModel
import io.github.occultus73.gitter.utils.StateResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var authRepository: AuthRepository

    @Mock
    private lateinit var firebaseHelper: FirebaseHelper

    @Mock
    private lateinit var viewStateFlow : Flow<StateResponse<AuthResult>>

    private lateinit var loginViewModel: LoginViewModel



    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        firebaseHelper = FirebaseHelper()
        authRepository = AuthRepository(firebaseHelper)
        loginViewModel = LoginViewModel(authRepository)
    }

    @Test
     fun `login with correct email and password`() {
        val email = "debintomdt@gmail.com"
        val password = "Debin@123"
        val result = authRepository.loginUser(email,password)
        Assert.assertEquals(StateResponse.success(result), result)
    }
}