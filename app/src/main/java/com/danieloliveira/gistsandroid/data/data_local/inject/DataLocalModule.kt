package com.danieloliveira.gistsandroid.data.data_local.inject

import android.content.Context
import androidx.room.Room
import com.danieloliveira.gistsandroid.data.data_local.data_sources.GistLocalDataSource
import com.danieloliveira.gistsandroid.data.data_local.db.AppDatabase
import com.danieloliveira.gistsandroid.data.data_source.GistDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataLocalModule {

    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "gists.db")
            .build()
    }
}

@InstallIn(SingletonComponent::class)
@Module
abstract class RemoteDataSource {

    @Named("cache")
    @Singleton
    @Binds
    abstract fun bindsGistDataSource(
        gistLocalDataSource: GistLocalDataSource
    ): GistDataSource
}