package com.danieloliveira.gistsandroid.data.data_remote.response

import com.google.gson.annotations.SerializedName

data class OwnerResponse(
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val imageUrl: String,
    @SerializedName("html_url")
    val url: String
)