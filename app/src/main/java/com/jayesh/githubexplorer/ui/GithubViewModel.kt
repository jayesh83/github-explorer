package com.jayesh.githubexplorer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jayesh.githubexplorer.data.GithubRepository
import com.jayesh.githubexplorer.data.model.PullRequest
import com.jayesh.githubexplorer.utils.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {
    private val _pullRequests: MutableStateFlow<Result<List<PullRequest>>> = MutableStateFlow(Result.Loading)
    val pullRequests = _pullRequests.asStateFlow()

    val pullRequestPages = githubRepository.getPullRequestPages().cachedIn(viewModelScope)

    fun getPullRequests() {
        _pullRequests.value = Result.Loading
        viewModelScope.launch {
            try {
                val pullRequests = githubRepository.getPullRequests()
                _pullRequests.value = Result.Success(pullRequests)
            } catch (exception: Exception) {
                _pullRequests.value = Result.Failure(exception)
            }
        }
    }
}