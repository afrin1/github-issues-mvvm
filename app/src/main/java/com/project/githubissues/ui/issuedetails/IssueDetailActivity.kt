package com.project.githubissues.ui.issuedetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.githubissues.R

class IssueDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.issue_detail_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, IssueDetailFragment.newInstance())
                .commitNow()
        }
    }
}