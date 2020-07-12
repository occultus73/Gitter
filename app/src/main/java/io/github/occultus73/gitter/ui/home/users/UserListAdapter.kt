package io.github.occultus73.gitter.ui.home.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.occultus73.gitter.R
import io.github.occultus73.gitter.model.room.User
import kotlinx.android.synthetic.main.item_layout_users.view.*

class UserListAdapter(
    private val clickListener: OnClickListener
) :
    ListAdapter<User, UserListAdapter.UserListViewHolder>(DiffCallback) {

    // Makes the list update intelligently, preserving ItemTouchHelper animations
    object DiffCallback: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.name == newItem.name
        }
    }

    inner class UserListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindUI(user: User) = with(itemView) {
            Glide.with(context).load(user.avatar_url).into(circular_imageView)
            name_textView.text = user.name
            repo_number_textView.text = user.repositories.toString()
        }

    }

    override fun getItemId(position: Int) = getItem(position).id.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return UserListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_users, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val user = getItem(position)
        holder.bindUI(user)
        holder.itemView.setOnClickListener {
            clickListener.onClick(user)
        }
    }

    class OnClickListener(val clickListener: (user: User) -> Unit) {
        fun onClick(user: User) = clickListener(user)
    }

}