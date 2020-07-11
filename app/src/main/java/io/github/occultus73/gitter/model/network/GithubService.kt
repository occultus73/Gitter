package io.github.occultus73.gitter.model.network

import androidx.lifecycle.LiveData
import io.github.occultus73.gitter.model.data.GithubRepository
import io.github.occultus73.gitter.utils.Constants.GET_REPOSITORIES
import io.github.occultus73.gitter.utils.Constants.USER_NAME
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET(GET_REPOSITORIES)
    fun getUserRepositories(@Path(USER_NAME) gitUserName: String): LiveData<List<GithubRepository>>
}
