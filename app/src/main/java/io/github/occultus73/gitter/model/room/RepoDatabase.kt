package io.github.occultus73.gitter.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.occultus73.gitter.model.data.GithubRepository

@Database(version = 2, entities = [GithubRepository::class])
abstract class RepoDatabase: RoomDatabase() {
    abstract fun getRepoDAO(): RepoDAO

    // Reso Coder's thread-safe singleton. Very elegant.
    companion object {
        @Volatile
        private var instance: RepoDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, RepoDatabase::class.java, "repos.db").build()
    }
}