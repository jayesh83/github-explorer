package com.jayesh.githubexplorer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jayesh.githubexplorer.data.model.PullRequest
import com.jayesh.githubexplorer.utils.data.ResultRenderer

@Composable
fun PullRequestScreen(viewModel: GithubViewModel) {
    val pullRequestState = viewModel.pullRequestsFlow.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getPullRequests()
    }

    ResultRenderer(
        state = pullRequestState,
        onLoading = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        },
        onError = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Something went wrong", style = MaterialTheme.typography.subtitle1)
            }
        },
        onSuccess = { prs ->
            PullRequestList(prs)
        }
    )
}

@Composable
fun PullRequestList(prs: List<PullRequest>) {
    LazyColumn {
        items(
            items = prs,
            key = { pr -> pr.id }
        ) { pr ->
            PullRequestItem(pr)
        }
    }
}

@Composable
fun PullRequestItem(pr: PullRequest) {
    Surface(shape = MaterialTheme.shapes.large) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(text = pr.title)
            Text(text = pr.user.name)
        }
    }
}