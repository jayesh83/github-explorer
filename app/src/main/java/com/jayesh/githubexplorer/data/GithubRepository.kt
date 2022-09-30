package com.jayesh.githubexplorer.data

import com.jayesh.githubexplorer.data.model.PullRequest

interface GithubRepository {
    suspend fun getPullRequests(): List<PullRequest>
}