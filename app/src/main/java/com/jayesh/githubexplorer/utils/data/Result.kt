package com.jayesh.githubexplorer.utils.data

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

sealed class Result<out R> {
    object Idle : Result<Nothing>()
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Failure(val exception: Exception) : Result<Nothing>()
}

@Composable
inline fun <T : Any> ResultRenderer(
    state: State<Result<T>>,
    crossinline onSuccess: @Composable (T) -> Unit,
    crossinline onError: @Composable (Exception) -> Unit = {},
    crossinline onLoading: @Composable () -> Unit = {}
) {
    Crossfade(targetState = state.value) {
        when (it) {
            is Result.Success -> onSuccess(it.data)
            is Result.Idle -> {} // Don't want to render anything when state is idle
            is Result.Failure -> onError(it.exception)
            Result.Loading -> onLoading()
        }
    }
}
