package com.jayesh.githubexplorer.data.network.api

import com.jayesh.githubexplorer.data.network.model.PullRequestParser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query

interface GithubApi {
    @GET("/repos/jayesh83/github-explorer/pulls")
    suspend fun getPullRequests(
        @HeaderMap headers: HashMap<String, Any>,
        @Query("state") prState: String = "closed",
        @Query("sort") sort: String = "created",
        @Query("direction") sortDirection: String = "desc",
        @Query("per_page") itemCountPerPage: Int,
        @Query("page") pageNumber: Int
    ): Response<List<PullRequestParser>>
}