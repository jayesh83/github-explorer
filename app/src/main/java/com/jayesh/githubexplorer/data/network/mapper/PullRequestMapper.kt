package com.jayesh.githubexplorer.data.network.mapper

import com.jayesh.githubexplorer.utils.data.Mapper
import com.jayesh.githubexplorer.data.model.PullRequest
import com.jayesh.githubexplorer.data.network.model.PullRequestParser
import javax.inject.Inject

interface PullRequestMapper : Mapper<PullRequestParser, PullRequest>

class PullRequestMapperImpl @Inject constructor(): PullRequestMapper {

    override fun mapFrom(from: PullRequestParser): PullRequest {
        return with(from) {
            PullRequest(
                id = id ?: -1L
            )
        }
    }
}