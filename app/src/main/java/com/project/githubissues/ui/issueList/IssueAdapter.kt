package com.project.githubissues.ui.issueList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.githubissues.R
import kotlinx.android.synthetic.main.issue_list_item.view.descriptionTextView
import kotlinx.android.synthetic.main.issue_list_item.view.titleTextView
import kotlinx.android.synthetic.main.issue_list_item.view.userTextView

class IssueAdapter: RecyclerView.Adapter<IssueItemViewHolder>() {
    var data =  listOf<Issue>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.issue_list_item, parent, false)
        return IssueItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: IssueItemViewHolder, position: Int) {
        val issue = data[position]
        holder.view.titleTextView.text = issue.title
        holder.view.descriptionTextView.text = issue.description
        holder.view.userTextView.text = "updated at ${issue.updatedAt} by ${issue.userName}"
    }
}

class IssueItemViewHolder(val view: View): RecyclerView.ViewHolder(view)
