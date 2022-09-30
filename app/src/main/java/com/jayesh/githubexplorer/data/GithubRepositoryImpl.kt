package com.jayesh.githubexplorer.data

import com.jayesh.githubexplorer.data.model.PullRequest
import com.jayesh.githubexplorer.data.network.GithubNetworkSource
import com.jayesh.githubexplorer.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
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
}