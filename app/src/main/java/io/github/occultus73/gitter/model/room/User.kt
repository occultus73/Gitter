package com.historymakers.gitter.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "avatar_url")
    var avatar_url: String?,
    @ColumnInfo(name = "repositories")
    var repositories: Int?
)