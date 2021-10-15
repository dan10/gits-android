package com.danieloliveira.gistsandroid.data.data_local.model

import androidx.room.Embedded
import androidx.room.Relation

data class GistWithFiles(
    @Embedded
    val gist: GistEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "gistId"
    )
    val files: List<FileEntity>
)