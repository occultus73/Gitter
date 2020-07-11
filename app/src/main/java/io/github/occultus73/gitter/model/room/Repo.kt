package com.historymakers.gitter.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repo_table")
data class Repo(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    @ColumnInfo(name = "repo_name")
    var name: String,
    @ColumnInfo(name = "owner_name")
    var owner_name: String,
    @ColumnInfo(name = "open_issues_count")
    var open_issues_count: Int,
    @ColumnInfo(name = "isPrivate")
    var isPrivate: Boolean,
    @ColumnInfo(name = "persisted_at")
    var persistedAt: String


)