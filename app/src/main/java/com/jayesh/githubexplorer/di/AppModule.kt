package com.jayesh.githubexplorer.di

import com.jayesh.commons.request.params.CommonRequestParams
import com.jayesh.commons.request.params.CommonRequestParamsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface AppModule {

    @Binds
    @Singleton
    fun bindCommonRequestParams(params: CommonRequestParamsImpl): CommonRequestParams

}