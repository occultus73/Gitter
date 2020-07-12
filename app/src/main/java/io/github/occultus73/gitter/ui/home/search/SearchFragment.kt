package io.github.occultus73.ui.home.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide


import io.github.occultus73.gitter.databinding.FragmentSearchBinding
import io.github.occultus73.gitter.model.data.UserResponse
import io.github.occultus73.gitter.model.room.UserDatabase
import io.github.occultus73.gitter.utils.Constants.SEARCH_USER
import io.github.occultus73.ui.home.HomeViewModel
import io.github.occultus73.ui.home.HomeViewModelFactory
import io.github.occultus73.ui.home.search.SearchFragmentDirections
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val application = requireNotNull(this.activity).application
        val dbDataSource = UserDatabase.getInstance(application).userDAO
        val homeViewModelFactory= HomeViewModelFactory(dbDataSource)
        viewModel = ViewModelProvider(this,homeViewModelFactory).get(HomeViewModel::class.java)
        binding.homeViewModel = viewModel
        binding.lifecycleOwner = this

        (activity as? AppCompatActivity)?.supportActionBar?.title = SEARCH_USER
        subscriberObserver()
    }

    private fun subscriberObserver() {
        viewModel.searchState.observe(viewLifecycleOwner, Observer<SearchStatus> { state ->
            when (state) {
                is SearchStatus.Loading -> {
                    if (state.loading) {
                        val imm =
                            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
                        progress_bar.visibility = View.VISIBLE
                    } else {
                        progress_bar.visibility = View.GONE
                    }
                }
                is SearchStatus.Success -> {
                    bindUI(state.userResponse)
                }
                is SearchStatus.Error -> Toast.makeText(activity,state.errorMsg,Toast.LENGTH_SHORT).show()
            }
        })


        viewModel.userAdded.observe(viewLifecycleOwner, Observer {added ->
            if (added==true){
                findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToUserListFragment())
            }

        })
    }

    private fun bindUI(userResponse: UserResponse) {
        user_details_container_cardView.visibility = View.VISIBLE
        add_user_button.visibility = View.VISIBLE
        activity?.let {Glide.with(it).load(userResponse.avatarUrl).into(circular_imageView)}
        name_textView.text = userResponse.login
        repo_number_textView.text = userResponse.publicRepos.toString()
    }

}

sealed class SearchStatus {
    data class Loading(var loading: Boolean) : SearchStatus()
    data class Success(var userResponse: UserResponse) : SearchStatus()
    data class Error(var errorMsg: String) : SearchStatus()
}
