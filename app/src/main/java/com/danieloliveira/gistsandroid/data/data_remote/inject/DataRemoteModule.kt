package com.danieloliveira.gistsandroid.data.data_remote.inject

import com.danieloliveira.gistsandroid.data.data_remote.data_source.GistRemoteDataSourceImpl
import com.danieloliveira.gistsandroid.data.data_remote.service.GistService
import com.danieloliveira.gistsandroid.data.data_source.GistDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataRemoteModule {

    @Singleton
    @Provides
    fun providesGistService(retrofit: Retrofit): GistService {
        return retrofit.create(GistService::class.java)
    }
}

@InstallIn(SingletonComponent::class)
@Module
abstract class RemoteDataSource {

    @Named("remote")
    @Singleton
    @Binds
    abstract fun bindsGistDataSource(
        gistRemoteDataSourceImpl: GistRemoteDataSourceImpl
    ): GistDataSource
}