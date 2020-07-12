package io.github.occultus73.ui.home.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import io.github.occultus73.gitter.databinding.FragmentUserListBinding
import io.github.occultus73.gitter.model.room.UserDatabase
import io.github.occultus73.gitter.utils.Constants.USER_TITLE
import io.github.occultus73.ui.home.HomeViewModel
import io.github.occultus73.ui.home.HomeViewModelFactory
import io.github.occultus73.ui.home.users.UserListFragmentDirections
import kotlinx.android.synthetic.main.fragment_user_list.*


class UserListFragment : Fragment() {

    lateinit var binding: FragmentUserListBinding
    lateinit var viewModel: HomeViewModel
    lateinit var userListAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val application = requireNotNull(activity).application
        val dbDataSource = UserDatabase.getInstance(application).userDAO
        val homeViewModelFactory = HomeViewModelFactory(dbDataSource)
        viewModel = ViewModelProvider(requireActivity(), homeViewModelFactory).get(HomeViewModel::class.java)
        binding.homeViewModel = viewModel
        binding.lifecycleOwner = this
        userListAdapter = UserListAdapter(mutableListOf(), UserListAdapter.OnClickListener {
            if (!it.name.isNullOrEmpty()) {
                findNavController().navigate(
                    UserListFragmentDirections.actionUserListFragmentToUserDetailsFragment()
                        .setUserName(it.name)
                )

            }
        })
        userList_recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        userList_recyclerView.adapter = userListAdapter

        (activity as? AppCompatActivity)?.supportActionBar?.title = USER_TITLE
        subscriberObserver()
    }

    private fun subscriberObserver() {
        viewModel.getUsersList().observe(viewLifecycleOwner, Observer { userList ->
            if (userList.isNotEmpty()) {
                userListAdapter.updateAdapterWithList(userList)
            }

        })

    }

}