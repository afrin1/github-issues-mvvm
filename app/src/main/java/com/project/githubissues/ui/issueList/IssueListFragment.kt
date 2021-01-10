package com.project.githubissues.ui.issueList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.project.githubissues.R
import com.project.githubissues.databinding.FragmentIssueListBinding
import com.project.githubissues.model.issuelist.Issue
import com.project.githubissues.model.issuelist.IssueService
import kotlinx.android.synthetic.main.fragment_issue_list.progressBar

class IssueListFragment : Fragment() {

    companion object {
        fun newInstance() = IssueListFragment()
    }

    private lateinit var viewModel: IssueListViewModel
    private lateinit var adapter: IssueAdapter

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentIssueListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_issue_list, container, false
        )
        adapter = IssueAdapter()
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFactory = IssueListViewModelFactory(IssueService.create())
        viewModel = ViewModelProvider(this, viewModelFactory).get(IssueListViewModel::class.java)
        // TODO: Use the ViewModel
        setIssuesObserver()
        setFetchErrorObserver()

        viewModel.getIssues()
    }

    private fun setFetchErrorObserver() {
        viewModel.fetchError.observe(viewLifecycleOwner, Observer {
            it?.let {
                hideProgressBar()
                Toast.makeText(
                    this.activity?.applicationContext,
                    "Issues List fetch Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun setIssuesObserver() {
        viewModel.issues.observe(viewLifecycleOwner, Observer {
            hideProgressBar()
            it?.let {
                setIssuesToListView(it)
            }
        })
    }

    private fun setIssuesToListView(it: List<Issue>) {
        adapter.data = it
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

}