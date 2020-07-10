package io.github.occultus73.gitter.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.occultus73.gitter.R
import io.github.occultus73.gitter.model.data.GithubRepository
import kotlinx.android.synthetic.main.repository_item.view.*

class RepositoryAdapter : ListAdapter<GithubRepository, RepositoryAdapter.RepoHolder>(DiffCallback) {

    // Compares new list with current internal list for changes - preserves nice rv animations
    private object DiffCallback: DiffUtil.ItemCallback<GithubRepository>() {

        override fun areItemsTheSame(oldItem: GithubRepository, newItem: GithubRepository): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: GithubRepository, newItem: GithubRepository): Boolean {
            return oldItem.git_url == newItem.git_url
        }

    }

    // Interface to manage when user clicks on item, but TODO: can we pass lambdas instead?
    interface OnItemClickListener {
        fun onItemClick(repo: GithubRepository)
    }

    private lateinit var  listener: OnItemClickListener

    inner class RepoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position))
                }
            }
        }

        fun bind(position: Int) {
            with(itemView) {
                val currentRepo: GithubRepository = getItem(position)

                text_view_repo_name.text = currentRepo.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.repository_item, parent, false)
        return RepoHolder(itemView)
    }

    override fun onBindViewHolder(holder: RepoHolder, position: Int) = holder.bind(position)

    fun getRepoAt(position: Int): GithubRepository? {
        return getItem(position)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}

