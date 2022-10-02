package com.jayesh.githubexplorer.data.network.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class PullRequestParser(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("created_at")
    val createdAt: Date?,

    @SerializedName("closed_at")
    val closedAt: Date?,

    @SerializedName("merged_at")
    val mergedAt: String?,

    @SerializedName("state")
    val state: String?,

    @SerializedName("user")
    val user: User?,

    @SerializedName("draft")
    val isDraft: Boolean?
) {
    data class User(
        @SerializedName("login")
        val name: String?,

        @SerializedName("id")
        val id: Long?,

        @SerializedName("avatar_url")
        val avatarUrl: String?
    )
}