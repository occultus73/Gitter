package io.github.occultus73.gitter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.auth.AuthResult
import io.github.occultus73.gitter.model.network.FirebaseHelper
import io.github.occultus73.gitter.ui.authentication.AuthRepository
import io.github.occultus73.gitter.utils.StateResponse
import kotlinx.coroutines.flow.Flow
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AuthRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var authRepository: AuthRepository

    @Mock
    private var firebaseHelper = FirebaseHelper()

    @Mock
    private lateinit var viewStateFlow : Flow<StateResponse<AuthResult>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        authRepository = AuthRepository(firebaseHelper)
    }

}