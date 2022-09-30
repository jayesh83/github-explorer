package com.jayesh.githubexplorer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jayesh.githubexplorer.R
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
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_merge),
                    contentDescription = "merge_icon",
                    tint = colorResource(R.color.purple_500),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 2.dp, end = 16.dp)
                ) {
                    Text(
                        text = pr.title,
                        style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.SemiBold),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = pr.user.name,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.alpha(0.7f)
                    )
                }
                Text(
                    text = pr.closedAt,
                    style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Normal),
                    modifier = Modifier
                        .alpha(0.8f)
                        .padding(top = 2.dp, end = 8.dp)
                )
            }
            Divider(
                thickness = 0.5.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}