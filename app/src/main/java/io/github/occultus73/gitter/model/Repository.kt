package io.github.occultus73.gitter.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import io.github.occultus73.gitter.model.data.GithubRepository
import io.github.occultus73.gitter.model.network.RetrofitClient
import io.github.occultus73.gitter.model.room.RepoDatabase

class Repository(context: Context) {
    private val roomRepoDatabase by lazy { Room.databaseBuilder(context, RepoDatabase::class.java, "repos.db").build() }
    private val roomRepoDAO by lazy { roomRepoDatabase.getRepoDAO() }

    val roomRepoData: LiveData<List<GithubRepository>> by lazy { roomRepoDAO.getAllRepos() }
    val retrofitRepoData: LiveData<List<GithubRepository>> by lazy { RetrofitClient.getRepositories("occultus73") }

    fun cacheRoom(){
        roomRepoDAO.insertAllRepos(retrofitRepoData.value)
    }
}