package com.danieloliveira.gistsandroid.data.data_local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class OwnerEntity(
     val login: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    val url: String
)