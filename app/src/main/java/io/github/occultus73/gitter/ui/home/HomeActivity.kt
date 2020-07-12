package io.github.occultus73.gitter.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import io.github.occultus73.gitter.R
import io.github.occultus73.gitter.model.network.FirebaseHelper
import io.github.occultus73.gitter.ui.authentication.AuthRepository
import io.github.occultus73.gitter.ui.authentication.AuthenticationActivity
import io.github.occultus73.gitter.ui.authentication.login.LoginFragment
import io.github.occultus73.gitter.utils.StateResponse
import io.github.occultus73.gitter.utils.makeSnackBar
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var navController: NavController
    private var firebaseHelper = FirebaseHelper()
    private lateinit var authRepository : AuthRepository
    private val uiScope = CoroutineScope(Dispatchers.IO)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        authRepository = AuthRepository(firebaseHelper)
        logout_imageview.setOnClickListener(this)
        navController = Navigation.findNavController(this, R.id.host_fragment)
        bottomNav.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController)

    }
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.logout_imageview -> {
                makeSnackBar(this@HomeActivity, v,
                    resources.getString(R.string.logout_message))
                    .setAction("YES") {
                        uiScope.launch {
                            logout()
                        }
                    }.show()


            }
        }
    }

    private suspend fun logout() {
        authRepository.logOut()
            .collect { state ->
                when (state) {
                    is StateResponse.Loading -> { }
                    is StateResponse.Success -> {
                     startActivity(Intent(this, HomeActivity::class.java))
                    }
                    is StateResponse.Failed -> { }
                }
            }
    }
}