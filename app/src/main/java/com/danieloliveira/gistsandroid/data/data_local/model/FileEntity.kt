package com.danieloliveira.gistsandroid.data.data_local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "files")
data class FileEntity(
    @PrimaryKey val gistId: String,
    @ColumnInfo(name = "file_name") val fileName: String,
    val type: String,
    val url: String
)