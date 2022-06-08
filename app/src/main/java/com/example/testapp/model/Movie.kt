package com.example.testapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("page") @Expose
    private var page: Int? = null,
    @SerializedName("total_results")
    @Expose
    private val totalResults: Int? = null,

    @SerializedName("total_pages")
    @Expose
    private val totalPages: Int? = null,

    @SerializedName("results")
    @Expose
    private val results: List<Result>? = null
)
