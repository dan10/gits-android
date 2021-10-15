package com.danieloliveira.gistsandroid.data.data_local.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gists")
class GistEntity(
    @PrimaryKey val id: String,
    val description: String,
    @Embedded val owner: OwnerEntity,
    @ColumnInfo(name = "gist_url") val gistUrl: String
)