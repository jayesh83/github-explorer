package com.jayesh.githubexplorer.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jayesh.githubexplorer.R
import com.jayesh.githubexplorer.commons.ErrorFullScreen
import com.jayesh.githubexplorer.commons.LoadingFullScreen
import com.jayesh.githubexplorer.commons.errorItem
import com.jayesh.githubexplorer.commons.loadingItem
import com.jayesh.githubexplorer.data.model.PullRequest

@Composable
fun PullRequestScreen(viewModel: GithubViewModel) {
    val pullRequestPages = viewModel.pullRequestPages.collectAsLazyPagingItems()
    PullRequestList(pullRequests = pullRequestPages)
}

@Composable
fun PullRequestList(pullRequests: LazyPagingItems<PullRequest>) {
    val noPullRequests by remember {
        derivedStateOf {
            with(pullRequests) {
                loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && itemCount == 0
            }
        }
    }

    LazyColumn {
        item {
            PullRequestHeader()
        }

        items(lazyPagingItems = pullRequests) { pr ->
            if (pr != null) {
                PullRequestItem(pr)
            }
        }

        when (val appendLoadState = pullRequests.loadState.append) {
            is LoadState.Loading -> loadingItem()
            is LoadState.Error -> errorItem(
                error = appendLoadState.error,
                onRetry = pullRequests::retry
            )
            else -> Unit
        }
    }

    Crossfade(targetState = pullRequests.loadState.refresh) { refreshLoadState ->
        when (refreshLoadState) {
            is LoadState.Loading -> LoadingFullScreen()
            is LoadState.Error -> ErrorFullScreen(
                error = refreshLoadState.error,
                onRetry = pullRequests::retry
            )
            else -> Unit
        }
    }

    AnimatedVisibility(
        visible = noPullRequests,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        NoPullRequestsMessage()
    }
}

@Composable
private fun NoPullRequestsMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.no_pr_message),
            style = MaterialTheme.typography.h6
        )
    }
}

@Composable
private fun PullRequestItem(pr: PullRequest) {
    Surface(
        modifier = Modifier.clickable(onClick = {})
    ) {
        Column {
            Row(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                if (pr.isMerged) {
                    Icon(
                        painter = painterResource(R.drawable.ic_merge),
                        contentDescription = "merge_icon",
                        tint = colorResource(R.color.purple_500),
                        modifier = Modifier.padding(start = 16.dp, end = 8.dp)
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_pr_closed),
                        contentDescription = "merge_icon",
                        tint = Color.Red,
                        modifier = Modifier.padding(start = 16.dp, end = 8.dp)
                    )
                }
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
                        modifier = Modifier.alpha(0.8f)
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(pr.user.avatarUrl)
                            .crossfade(true)
                            .placeholder(R.drawable.image_placeholder)
                            .build(),
                        contentDescription = "user image",
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clip(CircleShape)
                            .size(20.dp)
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

@Composable
fun PullRequestHeader() {
    Surface {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 56.dp)
                .padding(start = 20.dp, top = 16.dp, bottom = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.pull_requests),
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.SemiBold)
            )
        }
    }
}