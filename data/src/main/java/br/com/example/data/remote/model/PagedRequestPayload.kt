package br.com.example.data.remote.model

import com.google.gson.annotations.SerializedName

data class PagedRequestPayload<T>(
    @SerializedName("count") val count: Int = 0,
    @SerializedName("next") val next: String = "",
    @SerializedName("previous") val previous: String = "",
    @SerializedName("results") val results: List<T> = listOf()
)