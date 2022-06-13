package com.example.testapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Trending(
    @SerializedName("poster_path") @Expose
    var posterPath: String? = null,

    @SerializedName("overview")
    @Expose
    val overview: String? = null,

    @SerializedName("release_date")
    @Expose
    val releaseDate: String? = null,

    @SerializedName("title")
    @Expose
    val title: String? = null,

    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double? = null
)
