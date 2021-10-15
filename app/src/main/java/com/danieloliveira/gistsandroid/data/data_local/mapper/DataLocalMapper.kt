package com.danieloliveira.gistsandroid.data.data_local.mapper

import com.danieloliveira.gistsandroid.data.data_local.model.FileEntity
import com.danieloliveira.gistsandroid.data.data_local.model.GistEntity
import com.danieloliveira.gistsandroid.data.data_local.model.GistWithFiles
import com.danieloliveira.gistsandroid.data.data_local.model.OwnerEntity
import com.danieloliveira.gistsandroid.domain.model.FileModel
import com.danieloliveira.gistsandroid.domain.model.GistModel
import com.danieloliveira.gistsandroid.domain.model.OwnerModel

fun GistModel.toEntity(): GistEntity =
    GistEntity(
        id = this.id,
        description = this.description,
        owner = this.owner.toEntity(),
        gistUrl = this.url
    )

private fun OwnerModel.toEntity(): OwnerEntity =
    OwnerEntity(
        login = login,
        imageUrl = imageUrl,
        url = url
    )

fun GistModel.toFilesEntity(): List<FileEntity> =
    this.files.map { it.toEntity(this.id) }

fun FileModel.toEntity(id: String): FileEntity =
    FileEntity(
        gistId = id,
        fileName = fileName,
        type = type,
        url = url
    )

fun GistWithFiles.toModel(): GistModel =
    GistModel(
        id = this.gist.id,
        description = this.gist.description,
        owner = this.gist.owner.toModel(),
        files = this.files.toModels(),
        url = this.gist.gistUrl,
        isFavorite = true
    )

private fun List<FileEntity>.toModels(): List<FileModel> =
    this.map { it.toModel() }

private fun FileEntity.toModel(): FileModel =
    FileModel(
        fileName = fileName,
        type = type,
        url = url
    )

private fun OwnerEntity.toModel(): OwnerModel =
    OwnerModel(
        login = this.login,
        imageUrl = this.imageUrl,
        url = this.url
    )
