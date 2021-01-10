package com.project.githubissues.issueList.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IssuesListDatabaseDAO {
    @Query("SELECT * FROM issues_list_table ORDER BY id DESC")
    fun getIssuesList(): LiveData<List<IssueData>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIssues(issues: List<IssueData>)
}
