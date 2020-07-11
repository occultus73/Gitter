package io.github.occultus73.gitter.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.github.occultus73.gitter.databinding.LoginFragmentBinding
import io.github.occultus73.gitter.model.network.FirebaseHelper
import io.github.occultus73.gitter.ui.home.HomeActivity
import io.github.occultus73.gitter.utils.AuthListner
import io.github.occultus73.gitter.utils.CustomAlertDialog
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment(), AuthListner, CustomAlertDialog.OnOkButtonClick {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding : LoginFragmentBinding
  
    private var firebaseHelper = FirebaseHelper()
    private lateinit var customAlertDialog : CustomAlertDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, LoginViewModelFactory(firebaseHelper)).get(LoginViewModel::class.java)
        binding.loginViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.authListner = this
        customAlertDialog = CustomAlertDialog(this.requireContext(),this)
        goToSignUp()

    }

    override fun onLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        progress_bar.visibility = View.GONE
        startActivity(Intent(requireContext(), HomeActivity::class.java))
    }

    override fun onFailure(errorMessage: String) {
        progress_bar.visibility= View.GONE
        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
        customAlertDialog.CustomAlertDialog(errorMessage)
    }

    private fun goToSignUp() {
        viewModel.goToSignUp.observe(viewLifecycleOwner, Observer { state ->
            if(state == true) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
                viewModel.goToSignUpComplete()
            }
        } )
    }

    override fun onSuccessClick() {
      customAlertDialog.dismiss()
    }

    override fun onFailureClick() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
    }


}