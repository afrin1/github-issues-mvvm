package com.project.githubissues.ui.issueList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.project.githubissues.R

class IssueListFragment : Fragment() {

    companion object {
        fun newInstance() = IssueListFragment()
    }

    private lateinit var viewModel: IssueListViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_issue_list, container, false)
        val adapter = IssueAdapter()
        recyclerView= view.findViewById(R.id.issues_list)
        recyclerView.adapter = adapter
        viewModel = IssueListViewModel()
        viewModel.issues.observe(viewLifecycleOwner, Observer{
            it?.let {
                adapter.data = it
            }
        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(IssueListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}