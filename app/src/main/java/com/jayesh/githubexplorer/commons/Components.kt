package com.jayesh.githubexplorer.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jayesh.githubexplorer.R
import java.io.IOException

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
