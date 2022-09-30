package com.jayesh.githubexplorer.di

import com.jayesh.githubexplorer.data.GithubRepository
import com.jayesh.githubexplorer.data.GithubRepositoryImpl
import com.jayesh.githubexplorer.data.network.GithubNetworkSource
import com.jayesh.githubexplorer.data.network.GithubNetworkSourceImpl
import com.jayesh.githubexplorer.data.network.mapper.PullRequestMapper
import com.jayesh.githubexplorer.data.network.mapper.PullRequestMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindPullRequestMapper(mapper: PullRequestMapperImpl): PullRequestMapper

    @Binds
    @Singleton
    fun bindGithubNetworkSource(source: GithubNetworkSourceImpl): GithubNetworkSource

    @Binds
    @Singleton
    fun bindGithubRepository(repository: GithubRepositoryImpl): GithubRepository

}