package com.jayesh.githubexplorer.data.network.mapper

import com.jayesh.githubexplorer.data.model.PullRequest
import com.jayesh.githubexplorer.data.network.model.PullRequestParser
import com.jayesh.githubexplorer.utils.data.Mapper
import com.jayesh.githubexplorer.utils.formatGithubDate
import javax.inject.Inject

interface PullRequestMapper : Mapper<PullRequestParser, PullRequest>

class PullRequestMapperImpl @Inject constructor(): PullRequestMapper {

    override fun mapFrom(from: PullRequestParser): PullRequest {
        return with(from) {
            PullRequest(
                id = id ?: -1L,
                title = title ?: "",
                createdAt = formatGithubDate(createdAt),
                closedAt = formatGithubDate(closedAt),
                state = mapPullRequestState(state),
                user = mapPullRequestUser(user),
                isDraft = isDraft ?: false,
                isMerged = mergedAt != null
            )
        }
    }

    private fun mapPullRequestState(state: String?): PullRequest.State {
        return when (state) {
            "open" -> PullRequest.State.Open
            "closed" -> PullRequest.State.Closed
            "all" -> PullRequest.State.All
            else -> PullRequest.State.All
        }
    }

    private fun mapPullRequestUser(userParser: PullRequestParser.User?): PullRequest.User {
        if (userParser == null) {
            return PullRequest.User(-1L, "", "")
        }
        return with(userParser) {
            PullRequest.User(
                id = id ?: -1L,
                name = name ?: "",
                avatarUrl = avatarUrl ?: ""
            )
        }
    }
}