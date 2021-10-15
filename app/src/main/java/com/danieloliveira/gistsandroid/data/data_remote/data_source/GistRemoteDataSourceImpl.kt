package com.danieloliveira.gistsandroid.data.data_remote.data_source

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.danieloliveira.gistsandroid.data.data_remote.mapper.toModel
import com.danieloliveira.gistsandroid.data.data_remote.service.GistService
import com.danieloliveira.gistsandroid.data.data_source.GistDataSource
import com.danieloliveira.gistsandroid.domain.model.GistModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

@Named("remote")
class GistRemoteDataSourceImpl @Inject constructor(
    private val service: GistService
) : GistDataSource {

    override suspend fun getGists(page: Int, perPage: Int): List<GistModel> {
        return service.getGists(page = page, perPage = perPage).map { it.toModel() }
    }

    override fun getFavoriteGists(pagingConfig: PagingConfig): Flow<PagingData<GistModel>> {
        throw UnsupportedOperationException()
    }

    override suspend fun saveGist(gist: GistModel) {
        throw UnsupportedOperationException()
    }

    override suspend fun deleteGist(gist: GistModel) {
        throw UnsupportedOperationException()
    }

    override suspend fun isGistInFavorite(id: String): Boolean {
        throw UnsupportedOperationException()
    }
}