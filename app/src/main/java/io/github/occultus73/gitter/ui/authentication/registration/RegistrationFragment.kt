package io.github.occultus73.gitter.ui.authentication.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.github.occultus73.gitter.databinding.RegistrationFragmentBinding
import io.github.occultus73.gitter.model.network.FirebaseHelper
import io.github.occultus73.gitter.utils.AuthListner
import kotlinx.android.synthetic.main.registration_fragment.*

class RegistrationFragment : Fragment(), AuthListner {

    private lateinit var viewModel: RegistrationViewModel
    private lateinit var binding : RegistrationFragmentBinding
    private var firebaseHelper =
        FirebaseHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegistrationFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, RegistrationViewModelFactory(firebaseHelper))
            .get(RegistrationViewModel::class.java)
        binding.signUpViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.authListner = this
        goToLogin()
    }

    override fun onLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        progress_bar.visibility = View.GONE
    }

    override fun onFailure(errorMessage: String) {
        progress_bar.visibility = View.GONE
        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun goToLogin() {
        viewModel.goToLogin.observe(viewLifecycleOwner, Observer { state ->
            if(state == true) {
               findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment())
               viewModel.goToLoginComplete()
            }
        })
    }
}