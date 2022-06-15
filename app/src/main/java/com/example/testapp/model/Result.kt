package com.example.testapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("popularity") @Expose
    var popularity: Double? = null,
    @SerializedName("vote_count")
    @Expose
    val voteCount: Int? = null,

    @SerializedName("video")
    @Expose
    val video: Boolean? = null,

    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null,

    @SerializedName("id")
    @Expose
    val id: Int? = null,

    @SerializedName("adult")
    @Expose
    val adult: Boolean? = null,

    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: Any? = null,

    @SerializedName("original_language")
    @Expose
    val originalLanguage: String? = null,

    @SerializedName("original_title")
    @Expose
    val originalTitle: String? = null,

    @SerializedName("genre_ids")
    @Expose
    val genreIds: List<Int>? = null,

    @SerializedName("title")
    @Expose
    val title: String? = null,

    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double? = null,

    @SerializedName("overview")
    @Expose
    val overview: String? = null,

    @SerializedName("release_date")
    @Expose
    val releaseDate: String? = null
)
