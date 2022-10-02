package com.jayesh.githubexplorer.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jayesh.githubexplorer.data.model.PullRequest
import com.jayesh.githubexplorer.data.network.GithubNetworkSource
import javax.inject.Inject

class PullRequestPageSource @Inject constructor(
    private val network: GithubNetworkSource
) : PagingSource<Int, PullRequest>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PullRequest> {
        return try {
            val page = params.key ?: STARTING_PAGE_INDEX
            val pullRequests = network.getPullRequests(page = page, pageSize = params.loadSize)

            val nextPage = if (pullRequests.isEmpty()) {
                null
            } else {
                page + params.loadSize / NETWORK_PAGE_SIZE
            }

            val previousPage = if (page == 1) null else page - 1

            LoadResult.Page(
                data = pullRequests,
                prevKey = previousPage,
                nextKey = nextPage
            )
        } catch (exception: Throwable) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PullRequest>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
        const val NETWORK_PAGE_SIZE = 20
    }
}