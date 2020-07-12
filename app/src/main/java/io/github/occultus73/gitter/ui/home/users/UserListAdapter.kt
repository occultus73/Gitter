package io.github.occultus73.ui.home.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.occultus73.gitter.R
import io.github.occultus73.gitter.model.room.User
import kotlinx.android.synthetic.main.item_layout_users.view.*

class UserListAdapter(
    private val userList: MutableList<User>,
    private val clickListener: OnClickListener
) :
    RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {
    inner class UserListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindUI(user: User) = with(itemView) {
            Glide.with(context).load(user.avatar_url).into(circular_imageView)
            name_textView.text = user.name
            repo_number_textView.text = user.repositories.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return UserListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_users, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        var user = userList[position]
        holder.bindUI(user)
        holder.itemView.setOnClickListener {
            clickListener.onClick(user)
        }
    }

    fun updateAdapterWithList(list: List<User>) {
        userList.clear()
        userList.addAll(list)
        notifyDataSetChanged()
    }

    class OnClickListener(val clickListener: (user: User) -> Unit) {
        fun onClick(user: User) = clickListener(user)
    }

}