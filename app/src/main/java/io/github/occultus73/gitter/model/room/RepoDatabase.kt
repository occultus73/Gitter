package io.github.occultus73.gitter.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.occultus73.gitter.model.data.GithubRepository

@Database(version = 2, entities = [GithubRepository::class])
abstract class RepoDatabase: RoomDatabase() {
    abstract fun getRepoDAO(): RepoDAO
}