package com.project.githubissues.ui.issueList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.githubissues.R
import com.project.githubissues.model.database.IssueData
import com.project.githubissues.model.issuelist.Issue
import kotlinx.android.synthetic.main.issue_list_item.view.descriptionTextView
import kotlinx.android.synthetic.main.issue_list_item.view.titleTextView
import kotlinx.android.synthetic.main.issue_list_item.view.userTextView

class IssueAdapter : RecyclerView.Adapter<IssueItemViewHolder>() {
    var data = listOf<IssueData>()
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
        val continuation = if (issue.description.length > 200) "..." else ""

        holder.view.titleTextView.text = issue.title
        holder.view.descriptionTextView.text = "${issue.description.take(200)} $continuation"
        holder.view.userTextView.text =
            "last updated on ${issue.updatedAt.substring(0, 10)} by ${issue.username}"
    }
}

class IssueItemViewHolder(val view: View) : RecyclerView.ViewHolder(view)
