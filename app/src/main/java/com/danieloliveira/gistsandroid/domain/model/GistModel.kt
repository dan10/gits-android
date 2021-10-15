package com.danieloliveira.gistsandroid.domain.model

data class GistModel(
    val id: String,
    val description: String,
    val owner: OwnerModel,
    val url: String,
    val files: List<FileModel>,
    val isFavorite: Boolean = false
)

