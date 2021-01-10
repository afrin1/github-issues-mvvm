package com.project.githubissues.ui.issueList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.githubissues.model.issuelist.Issue
import com.project.githubissues.model.issuelist.IssueService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IssueListViewModel(issueService: IssueService) : ViewModel() {

    private val _fetchError = MutableLiveData<Boolean>()
    private val _issues = MutableLiveData<List<Issue>>()
    private val issueService = issueService

    val issues: LiveData<List<Issue>>
        get() = _issues
    val fetchError: LiveData<Boolean>
        get() = _fetchError

    fun getIssues() {
        issueService.getIssuesList().enqueue(object : Callback<List<Issue>> {
            override fun onResponse(call: Call<List<Issue>>, response: Response<List<Issue>>) {
                if (response.isSuccessful) {
                    _issues.postValue(response.body())
                } else {
                    _fetchError.postValue(true)
                }
            }

            override fun onFailure(call: Call<List<Issue>>, t: Throwable) {
                _fetchError.postValue(true)
            }
        })
    }
}