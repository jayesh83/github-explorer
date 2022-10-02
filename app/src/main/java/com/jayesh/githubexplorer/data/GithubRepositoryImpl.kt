package com.jayesh.githubexplorer.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jayesh.githubexplorer.data.model.PullRequest
import com.jayesh.githubexplorer.data.network.GithubNetworkSource
import com.jayesh.githubexplorer.data.paging.PullRequestPageSource
import com.jayesh.githubexplorer.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val network: GithubNetworkSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : GithubRepository {

    override suspend fun getPullRequests(): List<PullRequest> {
        return withContext(ioDispatcher) {
            network.getPullRequests()
        }
    }

    override fun getPullRequestPages(): Flow<PagingData<PullRequest>> {
        return Pager(
            config = PagingConfig(
                pageSize = PullRequestPageSource.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PullRequestPageSource(network)
            }
        ).flow
    }
}