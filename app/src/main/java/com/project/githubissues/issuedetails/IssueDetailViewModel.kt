package com.project.githubissues.issuedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.githubissues.issueList.model.database.IssueData

class IssueDetailViewModel : ViewModel() {
    private val _issues = MutableLiveData<List<IssueData>>()
    private lateinit var issues: LiveData<List<IssueData>>

    fun getIssueDetail(id: Long) {
    }
}