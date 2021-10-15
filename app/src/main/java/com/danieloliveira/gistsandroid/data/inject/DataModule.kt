package com.danieloliveira.gistsandroid.data.inject

import com.danieloliveira.gistsandroid.data.repository.GistRepositoryImpl
import com.danieloliveira.gistsandroid.domain.repository.GistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindsGistRepository(
        gistRepositoryImpl: GistRepositoryImpl
    ): GistRepository
}