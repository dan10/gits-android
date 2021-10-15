package com.danieloliveira.gistsandroid.paging

import androidx.paging.PagingSource
import com.danieloliveira.gistsandroid.data.data_local.data_sources.GistLocalDataSource
import com.danieloliveira.gistsandroid.data.data_remote.data_source.GistRemoteDataSourceImpl
import com.danieloliveira.gistsandroid.data.repository.GistRemotePagingSource
import com.danieloliveira.gistsandroid.domain.model.GistModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.RuntimeException
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class PagingSourceTest {

    @Test
    fun `when receive empty data in first time the keys should be null`() = runBlockingTest {
        val localDataSource = mockk<GistLocalDataSource>()
        val remoteDataSource = mockk<GistRemoteDataSourceImpl>()

        coEvery { remoteDataSource.getGists(any(), any()) } returns listOf()

        val pagingSource = GistRemotePagingSource(
            dataSource = remoteDataSource,
            localDataSource = localDataSource
        )
        assertEquals(
            expected = PagingSource.LoadResult.Page<Int, GistModel>(
                data = emptyList(),
                prevKey = null,
                nextKey = null,
            ), actual = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 10,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `when receive the data it should be modified because it's going to be verified if it is in the database `() =
        runBlockingTest {
            val localDataSource = mockk<GistLocalDataSource>()
            val remoteDataSource = mockk<GistRemoteDataSourceImpl>()

            val mockList = listOf<GistModel>(mockk(relaxed = true), mockk(relaxed = true))
            coEvery { remoteDataSource.getGists(any(), any()) } returns mockList
            coEvery { localDataSource.isGistInFavorite(any()) } returns false

            val pagingSource = GistRemotePagingSource(
                dataSource = remoteDataSource,
                localDataSource = localDataSource
            )
            assertEquals(
                expected = PagingSource.LoadResult.Page<Int, GistModel>(
                    data = mockList.map { it.copy() },
                    prevKey = null,
                    nextKey = 2,
                ), actual = pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = 1,
                        loadSize = 10,
                        placeholdersEnabled = false
                    )
                )
            )
        }

    @Test
    fun `If the received data is in the database, the isFavorite var should be set to true`() =
        runBlockingTest {
            val localDataSource = mockk<GistLocalDataSource>()
            val remoteDataSource = mockk<GistRemoteDataSourceImpl>()

            val mockList = listOf<GistModel>(mockk(relaxed = true), mockk(relaxed = true))
            coEvery { remoteDataSource.getGists(any(), any()) } returns mockList
            coEvery { localDataSource.isGistInFavorite(any()) } returns true

            val pagingSource = GistRemotePagingSource(
                dataSource = remoteDataSource,
                localDataSource = localDataSource
            )
            assertEquals(
                expected = PagingSource.LoadResult.Page<Int, GistModel>(
                    data = mockList.map { it.copy(isFavorite = true) },
                    prevKey = null,
                    nextKey = 2,
                ), actual = pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = 1,
                        loadSize = 10,
                        placeholdersEnabled = false
                    )
                )
            )
        }

    @Test
    fun `when receive exception from dataSources the paging should return a LoadResult Error wrapping the exception`() =
        runBlockingTest {
            val localDataSource = mockk<GistLocalDataSource>()
            val remoteDataSource = mockk<GistRemoteDataSourceImpl>()

            val mockList = listOf<GistModel>(mockk(relaxed = true), mockk(relaxed = true))
            val throwable = RuntimeException()

            coEvery { remoteDataSource.getGists(any(), any()) } throws throwable
            coEvery { localDataSource.isGistInFavorite(any()) } returns true

            val pagingSource = GistRemotePagingSource(
                dataSource = remoteDataSource,
                localDataSource = localDataSource
            )
            assertEquals(
                expected = PagingSource.LoadResult.Error(throwable),
                actual = pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = 1,
                        loadSize = 10,
                        placeholdersEnabled = false
                    )
                )
            )
        }
}