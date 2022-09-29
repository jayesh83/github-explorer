package com.jayesh.githubexplorer

import android.app.Application
import com.jayesh.commons.request.params.CommonRequestParams
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class GithubExplorerApplication : Application() {
    @Inject
    lateinit var commonRequestParams: CommonRequestParams

    override fun onCreate() {
        super.onCreate()
        commonRequestParams.init(token = BuildConfig.GITHUB_ACCESS_TOKEN_KEY)
    }
}