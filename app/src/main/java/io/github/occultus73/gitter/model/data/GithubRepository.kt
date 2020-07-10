package io.github.occultus73.gitter.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "repo_table")
data class GithubRepository(

    var archive_url: String?,
    var archived: Boolean?,
    var assignees_url: String?,
    var brobs_url: String?,
    var branches_url: String?,
    var clone_url: String?,
    var collaborators_url: String?,
    var comments_url: String?,
    var commits_url: String?,
    var compare_url: String?,
    var contents_url: String?,
    var contributors_url: String?,
    var created_at: String?,
    var default_branch: String?,
    var deployments_url: String?,
    var description: String?,
    var disabled: Boolean?,
    var downloads_url: String?,
    var events_url: String?,
    var fork: Boolean?,
    var forks: Int?,
    var forks_count: Int?,
    var forks_url: String?,
    var full_name: String?,
    var git_commits_url: String?,
    var git_refs_url: String?,
    var git_tags_url: String?,
    var git_url: String?,
    var has_downloads: Boolean?,
    var has_issues: Boolean?,
    var has_pages: Boolean?,
    var has_projects: Boolean?,
    var has_wiki: Boolean?,
    var homepage: String?,
    var hooks_url: String?,
    var html_url: String?,
    @PrimaryKey
    var id: Int?,
    var issue_comment_url: String?,
    var issue_events_url: String?,
    var issues_url: String?,
    var keys_url: String?,
    var labels_url: String?,
    var language: String?,
    var languages_url: String?,
    var license: String?,
    var merges_url: String?,
    var milestones_url: String?,
    var mirror_url: String?,
    var name: String?,
    var node_id: String?,
    var notifications_url: String?,
    var open_issues: Int?,
    var open_issues_count: Int?,
//    @Ignore
//    var owner: GithubOwner?,
    @SerializedName("private")
    var isPrivate: Boolean?,
    var pulls_url: String?,
    var pushed_at: String?,
    var releases_url: String?,
    var size: Int?,
    var ssh_url: String?,
    var stargazers_count: Int?,
    var stargazers_url: String?,
    var statuses_url: String?,
    var subscribers_url: String?,
    var subscription_url: String?,
    var svn_url: String?,
    var tags_url: String?,
    var teams_url: String?,
    var trees_url: String?,
    var updated_at: String?,
    var url: String?,
    var watchers: Int?,
    var watchers_count: Int?
)