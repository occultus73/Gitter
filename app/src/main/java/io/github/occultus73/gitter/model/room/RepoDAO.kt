package io.github.occultus73.gitter.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.occultus73.gitter.model.data.GithubRepository

@Dao
interface RepoDAO {
    @Insert
    fun insertRepo(repo: GithubRepository?)

    @Insert
    fun insertAllRepos(repos: List<GithubRepository>?)

    @Query("SELECT * FROM repo_table")
    fun getAllRepos(): LiveData<List<GithubRepository>>

    @Delete
    fun deleteRepo(note: GithubRepository?)

    @Query("DELETE FROM repo_table")
    fun deleteAllRepos()
}