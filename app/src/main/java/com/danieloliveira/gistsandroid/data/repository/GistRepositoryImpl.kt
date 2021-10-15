package com.danieloliveira.gistsandroid.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.danieloliveira.gistsandroid.data.data_source.GistDataSource
import com.danieloliveira.gistsandroid.domain.model.GistModel
import com.danieloliveira.gistsandroid.domain.repository.GistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class GistRepositoryImpl @Inject constructor(
    @Named("cache") private val localDataSource: GistDataSource,
    @Named("remote") private val remotePagingSource: GistDataSource
) : GistRepository {

    override fun getFavoriteGists(pagingConfig: PagingConfig): Flow<PagingData<GistModel>> {
        return localDataSource.getFavoriteGists(pagingConfig)
    }

    override fun getGists(pagingConfig: PagingConfig): Flow<PagingData<GistModel>> {
        return Pager(
            config = pagingConfig
        ) {
            GistRemotePagingSource(remotePagingSource, localDataSource)
        }.flow
    }

    override suspend fun saveOrRemoveGist(gist: GistModel) {
        if (!localDataSource.isGistInFavorite(gist.id)) {
            localDataSource.saveGist(gist)
        } else {
            localDataSource.deleteGist(gist)
        }
    }
}