package com.jayesh.githubexplorer.data.network

import com.jayesh.commons.request.params.CommonRequestParams
import com.jayesh.githubexplorer.data.model.PullRequest
import com.jayesh.githubexplorer.data.network.api.GithubApi
import com.jayesh.githubexplorer.data.network.mapper.PullRequestMapper
import com.jayesh.githubexplorer.utils.data.returnNoDataException
import javax.inject.Inject

class GithubNetworkSourceImpl @Inject constructor(
    private val commonRequestParams: CommonRequestParams,
    private val githubApi: GithubApi,
    private val pullRequestMapper: PullRequestMapper
) : GithubNetworkSource {

    override suspend fun getPullRequests(page: Int, pageSize: Int): List<PullRequest> {
        val response = githubApi.getPullRequests(
            headers = commonRequestParams.getHeaderMap(),
            pageNumber = page,
            itemCountPerPage = pageSize
        )
        if (response.isSuccessful) {
            response.body()?.let { pullRequests ->
                return pullRequests.map { pullRequestMapper.mapFrom(it) }
            }
        }
        returnNoDataException()
    }

}