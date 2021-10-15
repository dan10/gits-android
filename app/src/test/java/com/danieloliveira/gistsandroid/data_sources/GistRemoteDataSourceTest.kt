package com.danieloliveira.gistsandroid.data_sources

import com.danieloliveira.gistsandroid.data.data_remote.data_source.GistRemoteDataSourceImpl
import com.danieloliveira.gistsandroid.data.data_remote.service.GistService
import com.danieloliveira.gistsandroid.domain.model.GistModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.lang.RuntimeException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
class GistRemoteDataSourceTest {

    @Test
    fun `when call getGists the data received should be the list from service`() = runBlockingTest {
        val service = mockk<GistService>()
        val dataSource = GistRemoteDataSourceImpl(service)

        coEvery { service.getGists(any(), any()) } returns emptyList()

        assertEquals(expected = emptyList<GistModel>(), dataSource.getGists(1, 10))
    }

    @Test
    fun `when call getGists and we receive an error the exception to be threw should be equal to the one received from the service`() = runBlockingTest {
        val service = mockk<GistService>()
        val dataSource = GistRemoteDataSourceImpl(service)

        coEvery { service.getGists(any(), any()) } throws RuntimeException()

        assertFailsWith<RuntimeException> { dataSource.getGists(1, 10) }
    }

    @Test
    fun `when call getFavoriteGists should throw unsupported exception`() {
        val service = mockk<GistService>()
        val dataSource = GistRemoteDataSourceImpl(service)

        assertFailsWith<UnsupportedOperationException> { dataSource.getFavoriteGists(mockk()) }
    }

    @Test
    fun `when call saveGists should throw unsupported exception`() = runBlockingTest {
        val service = mockk<GistService>()
        val dataSource = GistRemoteDataSourceImpl(service)

        assertFailsWith<UnsupportedOperationException> { dataSource.saveGist(mockk()) }
    }

    @Test
    fun `when call deleteGists should throw unsupported exception`() = runBlockingTest {
        val service = mockk<GistService>()
        val dataSource = GistRemoteDataSourceImpl(service)

        assertFailsWith<UnsupportedOperationException> { dataSource.deleteGist(mockk()) }
    }

    @Test
    fun `when call isGistInFavorite should throw unsupported exception`() = runBlockingTest {
        val service = mockk<GistService>()
        val dataSource = GistRemoteDataSourceImpl(service)

        assertFailsWith<UnsupportedOperationException> { dataSource.isGistInFavorite("") }
    }

}