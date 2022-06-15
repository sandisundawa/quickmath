package com.example.testapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("page") @Expose
    var page: Int? = null,
    @SerializedName("total_results")
    @Expose
    val totalResults: Int? = null,

    @SerializedName("total_pages")
    @Expose
    val totalPages: Int? = null,

    @SerializedName("results")
    @Expose
    val results: List<Result>? = null
)
