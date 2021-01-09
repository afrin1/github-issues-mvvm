package com.project.githubissues.ui.issueList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.project.githubissues.R
import com.project.githubissues.databinding.FragmentIssueListBinding

class IssueListFragment : Fragment() {

    companion object {
        fun newInstance() = IssueListFragment()
    }

    private lateinit var viewModel: IssueListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentIssueListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_issue_list, container, false
        )
        val adapter = IssueAdapter()
        binding.recyclerView.adapter = adapter

        val issueListViewModel = ViewModelProvider(this).get(IssueListViewModel::class.java)
        binding.issueListViewModel = issueListViewModel
        issueListViewModel.issues.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(IssueListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}