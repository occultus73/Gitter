package com.historymakers.gitter.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.historymakers.gitter.data.db.Repo
import com.historymakers.gitter.data.db.User
import com.historymakers.gitter.data.db.UserDAO
import com.historymakers.gitter.data.response.ReposItemResponse
import com.historymakers.gitter.data.response.UserResponse
import com.historymakers.gitter.view.home.search.SearchValidUserRepository
import com.historymakers.gitter.view.home.search.SearchStatus
import com.historymakers.gitter.view.home.userdetails.RepoByUserRepository
import com.historymakers.gitter.view.home.userdetails.RepoLoadingStatus
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.*

class HomeViewModel(private val userDAO: UserDAO) : ViewModel() {

    lateinit var user: User
    var searchUser = MutableLiveData<String>()
    private val searchRepository = SearchValidUserRepository()
    private val repoByUserRepository = RepoByUserRepository()

    private var viewModelJob = Job()
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _searchState = MutableLiveData<SearchStatus>()
    val searchState: LiveData<SearchStatus>
        get() = _searchState

    private val _repoLoadingState = MutableLiveData<RepoLoadingStatus>()
    val repoLoadingState: LiveData<RepoLoadingStatus>
        get() = _repoLoadingState

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>>
        get() = _userList
    lateinit var search: String

    private var _userAdded = MutableLiveData<Boolean>()
    val userAdded: LiveData<Boolean>
        get() = _userAdded

    private var _repoListFromDB = MutableLiveData<List<Repo>>()
    val repoListFromDB : LiveData<List<Repo>>
    get() = _repoListFromDB

    fun onSearchButtonClicked() {
        coroutineScope.launch {
            _searchState.value = SearchStatus.Loading(true)
            search = searchUser.value.orEmpty().trim()
            var result = searchRepository.searchUser(search)
            result.observeForever {
                _searchState.value = SearchStatus.Loading(false)
                if (it is UserResponse) {
                    _searchState.value = SearchStatus.Success(it)
                    user = User(it.id, it.login, it.avatarUrl, it.publicRepos)
                } else {
                    _searchState.value = SearchStatus.Error(it.toString())
                }
            }
        }
    }

    fun onAddButtonClicked() {
        search = searchUser.value.orEmpty().trim()
        if (search.isNullOrEmpty()) return
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                userDAO.upsertUser(user)
                _userAdded.postValue(true)
            }
        }

    }

    fun getUsersList(): LiveData<List<User>> {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                _userList.postValue(userDAO.getUsersList())
            }
        }
        return userList
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getReposByUser(userName: String, updatedTime: Date) {
        _repoLoadingState.value = RepoLoadingStatus.Loading(true)
        coroutineScope.launch {
            try {
                repoByUserRepository.getRepoListByUser(userName).observeForever {
                    if (it[0] is ReposItemResponse) {
                        _repoLoadingState.postValue(RepoLoadingStatus.Success(it as List<ReposItemResponse>))
                        for (item in it) {
                            var repo = Repo(
                                item.id,
                                item.name,
                                userName,
                                item.openIssuesCount,
                                item.isPrivate,
                                updatedTime.toString()
                            )
                            persistRepositoriesToDB(repo)

                        }

                    } else {
                        _repoLoadingState.postValue(RepoLoadingStatus.Error(it.toString()))
                    }
                }
            } catch (e: Exception) {
                _repoLoadingState.postValue(RepoLoadingStatus.Error(e.localizedMessage))

            }
        }
    }

    fun getReposFromDatabaseByUser(userName: String){
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO){
                _repoListFromDB.postValue(userDAO.getReposList(userName))
//                _repoLoadingState.postValue(RepoLoadingStatus.Success(_repoListFromDB as List<Repo>))


            }
        }

    }
    private fun persistRepositoriesToDB(repo: Repo) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO){
                userDAO.upsertRepos(repo)
//                getReposFromDatabaseByUser(repo.owner_name)
            }
        }
    }


}