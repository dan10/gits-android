package com.danieloliveira.gistsandroid.data.data_source

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.danieloliveira.gistsandroid.domain.model.GistModel
import kotlinx.coroutines.flow.Flow

interface GistDataSource {

    suspend fun getGists(page: Int, perPage: Int): List<GistModel>

    fun getFavoriteGists(pagingConfig: PagingConfig): Flow<PagingData<GistModel>>

    suspend fun saveGist(gist: GistModel)

    suspend fun deleteGist(gist: GistModel)

    suspend fun isGistInFavorite(id: String): Boolean
}