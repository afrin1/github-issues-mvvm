package com.project.githubissues.ui.issueList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.githubissues.model.database.IssueData
import com.project.githubissues.model.database.IssuesListDatabaseDAO
import com.project.githubissues.model.issuelist.Issue
import com.project.githubissues.model.issuelist.IssueService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IssueListViewModel(issueService: IssueService, issuesListDatabaseDAO: IssuesListDatabaseDAO) :
    ViewModel() {

    private val _fetchError = MutableLiveData<Boolean>()
    private val _issues = MutableLiveData<List<IssueData>>()
    private val issueService = issueService
    private val datasource = issuesListDatabaseDAO

    //    lateinit var issues: LiveData<List<IssueData>>
    val issues: LiveData<List<IssueData>> = datasource.getIssuesList()
    val fetchError: LiveData<Boolean>
        get() = _fetchError


    fun getIssues() {
//        issues = datasource.getIssuesList()
        fetchIssues()
    }

    private fun fetchIssues() {
        issueService.getIssuesList().enqueue(object : Callback<List<Issue>> {
            override fun onResponse(call: Call<List<Issue>>, response: Response<List<Issue>>) {
                if (response.isSuccessful) {
                    runBlocking {
                        launch(Unconfined) {
                            response.body()?.let { insertIssues(it) }
                        }
                    }
                } else {
                    _fetchError.postValue(true)
                }
            }

            override fun onFailure(call: Call<List<Issue>>, t: Throwable) {
                _fetchError.postValue(true)
            }
        })
    }

    suspend fun insertIssues(response: List<Issue>) = withContext(Dispatchers.IO) {
        val list = transformData(response)
        datasource.insertIssues(list)
    }

    private fun transformData(list: List<Issue>?): List<IssueData> {
        var dataList = mutableListOf<IssueData>()
        list?.map { item ->
            dataList.add(
                IssueData(
                    item.id,
                    item.title,
                    item.description,
                    item.updatedAt,
                    item.user.username,
                    item.user.avatar
                )
            )
        }
        return dataList
    }
}