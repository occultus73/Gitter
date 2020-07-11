package io.github.occultus73.gitter.ui.authentication.registration

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import io.github.occultus73.gitter.R
import io.github.occultus73.gitter.databinding.RegistrationFragmentBinding
import io.github.occultus73.gitter.network.FirebaseHelper
import io.github.occultus73.gitter.utils.AuthListner
import io.github.occultus73.gitter.utils.CustomAlertDialog
import kotlinx.android.synthetic.main.registration_fragment.*

class RegistrationFragment : Fragment(), AuthListner, CustomAlertDialog.OnOkButtonClick {

    private lateinit var viewModel: RegistrationViewModel
    private lateinit var binding : RegistrationFragmentBinding
    private var firebaseHelper = FirebaseHelper()
    private lateinit var customAlertDialog : CustomAlertDialog

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
        customAlertDialog = CustomAlertDialog(this.requireActivity(),this)
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
        customAlertDialog.CustomAlertDialog(errorMessage)

    }

    private fun goToLogin() {
        viewModel.goToLogin.observe(viewLifecycleOwner, Observer { state ->
            if(state == true) {
               findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment())
               viewModel.goToLoginComplete()
            }
        })
    }

    override fun onSuccessClick() {
        findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment())
    }

    override fun onFailureClick() {
        customAlertDialog.dismiss()
    }
}