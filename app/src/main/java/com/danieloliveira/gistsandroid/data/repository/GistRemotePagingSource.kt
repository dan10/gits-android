package com.danieloliveira.gistsandroid.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.danieloliveira.gistsandroid.data.data_source.GistDataSource
import com.danieloliveira.gistsandroid.domain.model.GistModel

class GistRemotePagingSource(
    private val dataSource: GistDataSource,
    private val localDataSource: GistDataSource
) : PagingSource<Int, GistModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GistModel> {
        val page = params.key ?: 1

        return try {
            val result = dataSource.getGists(page = page, perPage = params.loadSize).map {
                it.copy(isFavorite = localDataSource.isGistInFavorite(it.id))
            }
            LoadResult.Page(
                data = result,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (result.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GistModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}