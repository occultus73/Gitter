package com.historymakers.gitter.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY repositories ASC")
    fun getUsersList(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertRepos(repo: Repo)

    @Query("SELECT * FROM repo_table WHERE owner_name = :userName")
    fun getReposList(userName:String): List<Repo>
}