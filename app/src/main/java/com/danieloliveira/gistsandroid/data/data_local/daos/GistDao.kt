package com.danieloliveira.gistsandroid.data.data_local.daos

import androidx.paging.PagingSource
import androidx.room.*
import com.danieloliveira.gistsandroid.data.data_local.model.FileEntity
import com.danieloliveira.gistsandroid.data.data_local.model.GistEntity
import com.danieloliveira.gistsandroid.data.data_local.model.GistWithFiles

@Dao
interface GistDao {

    @Transaction
    @Query("SELECT * FROM gists")
    fun getGists(): PagingSource<Int, GistWithFiles>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFiles(files: List<FileEntity>)

    @Delete
    suspend fun deleteFiles(files: List<FileEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGist(vararg gists: GistEntity)

    @Delete
    suspend fun deleteGist(vararg gists: GistEntity)

    @Query("SELECT EXISTS(SELECT * FROM gists WHERE id = :id)")
    suspend fun isGistInFavorite(id: String): Boolean
}