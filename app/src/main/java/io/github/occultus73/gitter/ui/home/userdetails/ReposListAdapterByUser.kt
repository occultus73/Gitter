package io.github.occultus73.ui.home.userdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import io.github.occultus73.gitter.R
import io.github.occultus73.gitter.model.data.ReposItemResponse
import kotlinx.android.synthetic.main.item_layout_repos.view.*

class ReposListAdapterByUser(
    private var reposListResponse: MutableList<ReposItemResponse>,
    private var ownerName: String
) :
    RecyclerView.Adapter<ReposListAdapterByUser.ReposViewHolder>() {
    inner class ReposViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindRepositories(repository: ReposItemResponse) = with(itemView) {
            repo_name_textView.text = repository.name
            open_issuesCount_textView.text = repository.openIssuesCount.toString()
            repo_ownerName_textView.text = ownerName
            if (repository.isPrivate) {
                isPrivatePublic_textView.text = resources.getString(R.string.isPrivate)
            } else {
                isPrivatePublic_textView.text = resources.getString(R.string.isPublic)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        return ReposViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_repos, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return reposListResponse.size
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        var repository = reposListResponse[position]
        holder.bindRepositories(repository)
    }

    fun updateRepositoriesList(reposList: List<ReposItemResponse>) {
        reposListResponse.clear()
        reposListResponse.addAll(reposList)
        notifyDataSetChanged()

    }
}