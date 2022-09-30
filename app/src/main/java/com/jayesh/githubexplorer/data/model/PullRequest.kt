package com.jayesh.githubexplorer.data.model

data class PullRequest(
    val id: Long,
    val title: String,
    val createdAt: String,
    val closedAt: String,
    val state: State,
    val user: User,
    val isDraft: Boolean,
    val isMerged: Boolean
) {
    data class User(
        val id: Long,
        val name: String,
        val avatarUrl: String
    )

    enum class State {
        Open, Closed, All
    }

    val isClosed get() = state == State.Closed
    val isOpen get() = state == State.Open
}