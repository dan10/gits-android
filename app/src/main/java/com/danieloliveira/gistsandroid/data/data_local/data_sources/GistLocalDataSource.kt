package com.danieloliveira.gistsandroid.data.data_local.data_sources

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.danieloliveira.gistsandroid.data.data_local.db.AppDatabase
import com.danieloliveira.gistsandroid.data.data_local.mapper.toEntity
import com.danieloliveira.gistsandroid.data.data_local.mapper.toFilesEntity
import com.danieloliveira.gistsandroid.data.data_local.mapper.toModel
import com.danieloliveira.gistsandroid.data.data_source.GistDataSource
import com.danieloliveira.gistsandroid.domain.model.GistModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GistLocalDataSource @Inject constructor(
    private val db: AppDatabase
): GistDataSource {

    override suspend fun getGists(page: Int, perPage: Int): List<GistModel> {
        throw UnsupportedOperationException()
    }

    override  fun getFavoriteGists(pagingConfig: PagingConfig): Flow<PagingData<GistModel>> {
        return Pager(config = pagingConfig) {
            db.gistDao().getGists()
        }.flow.map { pagingData ->
            pagingData.map { it.toModel() }
        }
    }

    override suspend fun saveGist(gist: GistModel) {
        db.gistDao().insertFiles(gist.toFilesEntity())
        db.gistDao().insertGist(gist.toEntity())
    }

    override suspend fun deleteGist(gist: GistModel) {
        db.gistDao().deleteGist(gist.toEntity())
        db.gistDao().deleteFiles(gist.toFilesEntity())
    }

    override suspend fun isGistInFavorite(id: String): Boolean {
        return db.gistDao().isGistInFavorite(id)
    }
}