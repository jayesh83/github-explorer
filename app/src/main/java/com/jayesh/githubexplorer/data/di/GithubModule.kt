package com.jayesh.githubexplorer.data.di

import com.jayesh.githubexplorer.data.GithubRepository
import com.jayesh.githubexplorer.data.GithubRepositoryImpl
import com.jayesh.githubexplorer.data.network.GithubNetworkSource
import com.jayesh.githubexplorer.data.network.GithubNetworkSourceImpl
import com.jayesh.githubexplorer.data.network.api.GithubApi
import com.jayesh.githubexplorer.data.network.mapper.PullRequestMapper
import com.jayesh.githubexplorer.data.network.mapper.PullRequestMapperImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface GithubModule {

    @Binds
    @Singleton
    fun bindPullRequestMapper(mapper: PullRequestMapperImpl): PullRequestMapper

    @Binds
    @Singleton
    fun bindGithubNetworkSource(source: GithubNetworkSourceImpl): GithubNetworkSource

    /*@Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG){
        val loggingInterceptor =HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }else{
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()
    */
    @Provides
    @Singleton
    fun bindGithubApi(api: GithubApi): GithubNetworkSource

    @Binds
    @Singleton
    fun bindGithubRepository(repository: GithubRepositoryImpl): GithubRepository

}