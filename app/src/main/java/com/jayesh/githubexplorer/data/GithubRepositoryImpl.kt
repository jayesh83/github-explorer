package com.jayesh.githubexplorer.data

import com.jayesh.githubexplorer.data.model.PullRequest
import com.jayesh.githubexplorer.data.network.GithubNetworkSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val network: GithubNetworkSource,
    //private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GithubRepository {

    override suspend fun getPullRequests(): List<PullRequest> {
        return withContext(Dispatchers.IO) {
            network.getPullRequests()
        }
    }
}