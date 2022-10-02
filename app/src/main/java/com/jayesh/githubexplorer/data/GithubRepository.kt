package com.jayesh.githubexplorer.data

import androidx.paging.PagingData
import com.jayesh.githubexplorer.data.model.PullRequest
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun getPullRequests(): List<PullRequest>
    fun getPullRequestPages(): Flow<PagingData<PullRequest>>
}