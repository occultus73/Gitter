package io.github.occultus73.gitter.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import io.github.occultus73.gitter.model.data.GithubRepository
import io.github.occultus73.gitter.model.network.RetrofitClient
import io.github.occultus73.gitter.model.room.RepoDatabase

class DataRepository(context: Context) {
    private val roomRepoDatabase by lazy { RepoDatabase(context).getRepoDAO() }
    private val roomRepoData: LiveData<List<GithubRepository>> by lazy { roomRepoDatabase.getAllRepos() }

    fun requestGithubUserRepos(user: String) = RetrofitClient.getRepositories(user)
}