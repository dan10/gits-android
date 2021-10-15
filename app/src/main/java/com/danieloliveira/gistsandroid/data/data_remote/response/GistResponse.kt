package com.danieloliveira.gistsandroid.data.data_remote.response

import com.google.gson.annotations.SerializedName

data class GistResponse(
    @SerializedName("description")
    val description: String?,
    @SerializedName("owner")
    val owner: OwnerResponse,
    @SerializedName("html_url")
    val url: String,
    @SerializedName("files")
    val files: Map<String, FileResponse>,
    @SerializedName("id")
    val id: String
)

