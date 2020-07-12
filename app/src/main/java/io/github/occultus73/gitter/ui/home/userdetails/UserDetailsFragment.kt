package io.github.occultus73.ui.home.userdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.occultus73.gitter.databinding.FragmentUserDetailsBinding
import io.github.occultus73.gitter.model.data.ReposItemResponse
import io.github.occultus73.gitter.model.room.UserDatabase
import io.github.occultus73.gitter.ui.home.HomeActivity
import io.github.occultus73.ui.home.HomeViewModel
import io.github.occultus73.ui.home.HomeViewModelFactory
import io.github.occultus73.ui.home.userdetails.UserDetailsFragmentArgs
import kotlinx.android.synthetic.main.fragment_user_details.*
import java.util.*

class UserDetailsFragment : Fragment() {

    lateinit var updateTime: String
    lateinit var binding: FragmentUserDetailsBinding
    lateinit var viewModel: HomeViewModel
    lateinit var reposListAdapterByUser: ReposListAdapterByUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var username = arguments?.let { UserDetailsFragmentArgs.fromBundle(it).userName }
        (activity as HomeActivity).supportActionBar?.title = username
        val application = requireNotNull(this.activity).application
        val dbDataSource = UserDatabase.getInstance(application).userDAO
        val homeViewModelFactory = HomeViewModelFactory(dbDataSource)
        viewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
        binding.homeViewModel = viewModel
        binding.lifecycleOwner = this

        repoList_recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        reposListAdapterByUser = ReposListAdapterByUser(mutableListOf(), username!!)
        repoList_recyclerView.adapter = reposListAdapterByUser

        viewModel.getReposByUser(username!!, Calendar.getInstance().time)
        subscriberObserver()

    }

    private fun subscriberObserver() {
        viewModel.repoLoadingState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is RepoLoadingStatus.Loading -> progress_bar.visibility = View.VISIBLE
                is RepoLoadingStatus.Success -> {
                    bindUI(state.reposItemResponse)
                    progress_bar.visibility = View.GONE
                }
                is RepoLoadingStatus.Error -> {
                    progress_bar.visibility = View.GONE
                    Toast.makeText(activity, state.errorMsg, Toast.LENGTH_SHORT).show()
                }

            }
        })

//        viewModel.repoListFromDB.observe(viewLifecycleOwner, Observer {
//            updateTime = it[0].persistedAt
//        })
    }

    private fun bindUI(reposItemResponseList: List<ReposItemResponse>) {
        reposListAdapterByUser.updateRepositoriesList(reposItemResponseList)
    }

}

sealed class RepoLoadingStatus {
    data class Loading(var loading: Boolean) : RepoLoadingStatus()
    data class Success(var reposItemResponse: List<ReposItemResponse>) : RepoLoadingStatus()
    data class Error(var errorMsg: String) : RepoLoadingStatus()
}