package com.jayesh.githubexplorer.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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
import com.jayesh.githubexplorer.data.model.PullRequest
import java.io.IOException

@Composable
fun PullRequestScreen(viewModel: GithubViewModel) {
    val pullRequestPages = viewModel.pullRequestPages.collectAsLazyPagingItems()
    PullRequestList(pullRequests = pullRequestPages)
}

@Composable
fun PullRequestList(pullRequests: LazyPagingItems<PullRequest>) {
    LazyColumn {
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
}

@Composable
fun LoadingFullScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(56.dp))
    }
}

@Composable
fun ErrorFullScreen(error: Throwable, onRetry: () -> Unit) {
    val isNetworkIssue = error is IOException
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(if (isNetworkIssue) R.string.no_network_message else R.string.something_went_wrong),
                style = MaterialTheme.typography.subtitle1
            )
            Button(onClick = onRetry, modifier = Modifier.defaultMinSize(128.dp)) {
                Text(
                    text = stringResource(R.string.retry),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

fun LazyListScope.loadingItem() {
    item {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(56.dp)
            )
        }
    }
}

fun LazyListScope.errorItem(error: Throwable, onRetry: () -> Unit) {
    val isNetworkIssue = error is IOException
    item {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(if (isNetworkIssue) R.string.no_network_message else R.string.something_went_wrong),
                    style = MaterialTheme.typography.subtitle1
                )
                Button(onClick = onRetry) {
                    Text(
                        text = stringResource(R.string.retry),
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    }
}

@Composable
fun PullRequestItem(pr: PullRequest) {
    Surface(
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.clickable(onClick = {})
    ) {
        Column {
            Row(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_merge),
                    contentDescription = "merge_icon",
                    tint = colorResource(R.color.purple_500),
                    modifier = Modifier.padding(start = 16.dp, end = 8.dp)
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