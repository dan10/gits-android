package com.danieloliveira.gistsandroid.domain.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.danieloliveira.gistsandroid.domain.model.GistModel
import kotlinx.coroutines.flow.Flow


interface GistRepository {

    fun getFavoriteGists(pagingConfig: PagingConfig): Flow<PagingData<GistModel>>

    fun getGists(pagingConfig: PagingConfig): Flow<PagingData<GistModel>>

    suspend fun saveOrRemoveGist(gist: GistModel)
}