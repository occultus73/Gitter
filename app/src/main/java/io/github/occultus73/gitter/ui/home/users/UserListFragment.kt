package io.github.occultus73.ui.home.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import io.github.occultus73.gitter.databinding.FragmentUserListBinding
import io.github.occultus73.gitter.model.room.UserDatabase
import io.github.occultus73.gitter.ui.home.users.UserListAdapter
import io.github.occultus73.gitter.utils.Constants.USER_TITLE
import io.github.occultus73.ui.home.HomeViewModel
import io.github.occultus73.ui.home.HomeViewModelFactory
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
        userListAdapter = UserListAdapter(UserListAdapter.OnClickListener {
            if (!it.name.isNullOrEmpty()) {
                findNavController().navigate(
                    UserListFragmentDirections.actionUserListFragmentToUserDetailsFragment()
                        .setUserName(it.name)
                )

            }
        })

        //update the recycler view reactively
        viewModel.getUsersList().observe(viewLifecycleOwner, Observer {userListAdapter.submitList(it)})

        //add swipe-to delete functionality
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                recyclerView.adapter
                return false
            }

            //code to delete user from database when swiped
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                userListAdapter.getItemId(viewHolder.adapterPosition).let { viewModel.deleteUserById(it.toInt()) }
                viewModel.getUsersList().observe(viewLifecycleOwner, Observer {userListAdapter.submitList(it)})}
        }).attachToRecyclerView(userList_recyclerView)



        userList_recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        userList_recyclerView.adapter = userListAdapter

        (activity as? AppCompatActivity)?.supportActionBar?.title = USER_TITLE
    }

}