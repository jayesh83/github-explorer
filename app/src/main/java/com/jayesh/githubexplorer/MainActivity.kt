package com.jayesh.githubexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jayesh.githubexplorer.ui.GithubViewModel
import com.jayesh.githubexplorer.ui.PullRequestScreen
import com.jayesh.githubexplorer.ui.theme.GithubExplorerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubExplorerTheme {
                val githubViewModel: GithubViewModel = viewModel()
                PullRequestScreen(viewModel = githubViewModel)
            }
        }
    }
}