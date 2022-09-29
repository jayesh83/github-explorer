package com.jayesh.githubexplorer.data.network

import com.jayesh.githubexplorer.data.model.PullRequest

interface GithubNetworkSource {
    suspend fun getPullRequests(): List<PullRequest>
}