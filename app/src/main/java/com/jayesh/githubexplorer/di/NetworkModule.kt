package com.jayesh.githubexplorer.di

import com.jayesh.githubexplorer.BuildConfig
import com.jayesh.githubexplorer.data.network.api.GithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    private val baseRetrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BuildConfig.GITHUB_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    private val okHttpClientBuilder = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)

    @Provides
    fun provideGithubService(): GithubApi {
        return baseRetrofitBuilder
            .client(okHttpClientBuilder.build())
            .build()
            .create(GithubApi::class.java)
    }
}