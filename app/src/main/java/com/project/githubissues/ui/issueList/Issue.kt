package com.project.githubissues.ui.issueList

data class Issue(
    val id: Long = 0L,
    val title: String,
    val description: String,
    val updatedAt: String,
    val userName: String,
    val avatar: String
)
