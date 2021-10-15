package com.danieloliveira.gistsandroid.data.data_remote.mapper

import com.danieloliveira.gistsandroid.data.data_remote.response.FileResponse
import com.danieloliveira.gistsandroid.data.data_remote.response.GistResponse
import com.danieloliveira.gistsandroid.data.data_remote.response.OwnerResponse
import com.danieloliveira.gistsandroid.domain.model.FileModel
import com.danieloliveira.gistsandroid.domain.model.GistModel
import com.danieloliveira.gistsandroid.domain.model.OwnerModel

fun GistResponse.toModel(): GistModel =
    GistModel(
        description = this.description.orEmpty(),
        files = this.files.map { it.value }.toModel(),
        owner = this.owner.toModel(),
        url = this.url,
        id = this.id
    )

private fun OwnerResponse.toModel(): OwnerModel =
    OwnerModel(
        login =this.login,
        imageUrl = this.imageUrl,
        url = this.url
    )

fun List<FileResponse>.toModel(): List<FileModel> =
    this.map { it.toModel() }


fun FileResponse.toModel(): FileModel =
    FileModel(
        fileName = this.fileName,
        type = this.type,
        url = this.url
    )