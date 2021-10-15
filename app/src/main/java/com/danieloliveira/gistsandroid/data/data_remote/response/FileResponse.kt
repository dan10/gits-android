package com.danieloliveira.gistsandroid.data.data_remote.response

import com.google.gson.annotations.SerializedName

class FileResponse(
    @SerializedName("filename")
    val fileName: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("raw_url")
    val url: String,
)
