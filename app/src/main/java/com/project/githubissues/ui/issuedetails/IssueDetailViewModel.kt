package com.project.githubissues.ui.issuedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.githubissues.ui.issueList.model.database.IssueData

class IssueDetailViewModel : ViewModel() {
    private val _issues = MutableLiveData<List<IssueData>>()
    private lateinit var issues: LiveData<List<IssueData>>

    fun getIssueDetail(id: Long) {
    }
}