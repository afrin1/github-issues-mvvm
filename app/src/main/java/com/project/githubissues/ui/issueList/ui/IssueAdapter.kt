package com.project.githubissues.ui.issueList.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.githubissues.R
import com.project.githubissues.databinding.IssueListItemBinding
import com.project.githubissues.ui.issueList.model.database.IssueData
import kotlinx.android.synthetic.main.issue_list_item.view.descriptionTextView
import kotlinx.android.synthetic.main.issue_list_item.view.titleTextView
import kotlinx.android.synthetic.main.issue_list_item.view.userTextView

class IssueAdapter(val clickListener: IssueListListener) :
    RecyclerView.Adapter<IssueItemViewHolder>() {
    var data = listOf<IssueData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
//        val view = layoutInflater
//            .inflate(R.layout.issue_list_item, parent, false)
        val binding: IssueListItemBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.issue_list_item, parent, false
        )
        return IssueItemViewHolder(binding.root, binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: IssueItemViewHolder, position: Int) {
        val issue = data[position]
        holder.bind(issue, clickListener)
        val continuation = if (issue.description.length > 200) "..." else ""
        holder.view.titleTextView.text = issue.title
        holder.view.descriptionTextView.text = "${issue.description.take(200)} $continuation"
        holder.view.userTextView.text =
            "last updated on ${issue.updatedAt.substring(0, 10)} by ${issue.username}"
    }
}

class IssueItemViewHolder(
    val view: View, val binding: IssueListItemBinding
) : RecyclerView.ViewHolder(view) {

    fun bind(data: IssueData, clickListener: IssueListListener) {
        binding.issue = data
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }
}

class IssueListListener(val clickListener: (id: Long) -> Unit) {
    fun onClick(issue: IssueData) = clickListener(issue.id)
}
