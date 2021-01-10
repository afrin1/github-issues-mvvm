package com.project.githubissues.ui.issueList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.githubissues.model.issuelist.IssueService

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the SleepDatabaseDao and context to the ViewModel.
 */
class IssueListViewModelFactory(
    private val issueService: IssueService
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IssueListViewModel::class.java)) {
            return IssueListViewModel(issueService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}