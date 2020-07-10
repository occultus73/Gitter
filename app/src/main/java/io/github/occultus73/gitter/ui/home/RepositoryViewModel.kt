package io.github.occultus73.gitter.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.github.occultus73.gitter.model.Repository
import io.github.occultus73.gitter.model.data.GithubRepository

class RepositoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository = Repository(application)

    val roomRepoData: LiveData<List<GithubRepository>> = repository.roomRepoData
    val retrofitRepoData: LiveData<List<GithubRepository>> = repository.retrofitRepoData

    init {
        repository.cacheRoom()
    }
}