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
        @Query("per_page") itemCountPerPage: Int = 30,
        @Query("page") pageNumber: Int = 1
    ): Response<List<PullRequestParser>>
    //state Default: open
    //
    //Can be one of: open, closed, all

    // sort Default: created
    //
    //Can be one of: created, updated, popularity, long-running
}