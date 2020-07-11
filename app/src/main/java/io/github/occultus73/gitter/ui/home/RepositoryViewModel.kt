package io.github.occultus73.gitter.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.github.occultus73.gitter.model.DataRepository
import io.github.occultus73.gitter.model.data.GithubRepository

class RepositoryViewModel(application: Application) : AndroidViewModel(application) {
    private val dataRepository: DataRepository = DataRepository(application)

}