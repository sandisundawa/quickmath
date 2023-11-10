package com.moviewers.testapp.testapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("id") @Expose
    var id: Int? = null,
    @SerializedName("page")
    @Expose
    private val page: Int? = null,

    @SerializedName("results")
    @Expose
    val results: List<ResultReview>? = null,

    @SerializedName("total_pages")
    @Expose
    private val totalPages: Int? = null,

    @SerializedName("total_results")
    @Expose
    private val totalResults: Int? = null
)
