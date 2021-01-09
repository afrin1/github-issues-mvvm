package com.project.githubissues.ui.issueList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IssueListViewModel : ViewModel() {
    private val _issues = getIssues()
    val isProgressVisible: Boolean = true
    val issues: LiveData<List<Issue>>
        get() = _issues

    private fun getIssues(): MutableLiveData<List<Issue>> {
        var data: MutableLiveData<List<Issue>> = MutableLiveData()
        val issue = Issue(1, "Title", "Description", "updated At", "username", "vatar url")
        val issue2 =
            Issue(2, "Title 2", "Description 2", "updated At 2", "username 2", "vatar url 2")
        data.value = listOf(issue, issue2)
        return data
    }

}