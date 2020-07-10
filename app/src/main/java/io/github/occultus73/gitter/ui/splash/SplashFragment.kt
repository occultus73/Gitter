package io.github.occultus73.gitter.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.github.occultus73.gitter.R
import io.github.occultus73.gitter.network.FirebaseHelper

class SplashFragment : Fragment() {

    private var firebaseHelper = FirebaseHelper()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
               if(firebaseHelper.firebaseAuth.currentUser?.uid != null &&
                       firebaseHelper.firebaseAuth.currentUser?.isEmailVerified == true)  {
                   findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
               } else {
                   findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
               }
            }, 2000
        )
    }
}