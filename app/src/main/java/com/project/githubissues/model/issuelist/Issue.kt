package com.project.githubissues.model.issuelist

import com.google.gson.annotations.SerializedName

data class Issue(
    val id: Long = 0L,
    val title: String,
    @SerializedName("body")
    val description: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("login")
    val userName: String,
    @SerializedName("avatar_url")
    val avatar: String
)
