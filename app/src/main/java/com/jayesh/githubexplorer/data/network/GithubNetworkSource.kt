package com.jayesh.githubexplorer.data.network

import com.jayesh.githubexplorer.data.model.PullRequest
import com.jayesh.githubexplorer.data.paging.PullRequestPageSource

interface GithubNetworkSource {
    suspend fun getPullRequests(
        page: Int = PullRequestPageSource.STARTING_PAGE_INDEX,
        pageSize: Int = PullRequestPageSource.NETWORK_PAGE_SIZE
    ): List<PullRequest>
}