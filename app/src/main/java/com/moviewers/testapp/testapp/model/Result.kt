package com.moviewers.testapp.testapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Result(
    @SerializedName("popularity")
    @Expose
    @ColumnInfo(name = "popularity") var popularity: Double? = null,

    @SerializedName("vote_count")
    @Expose
    @ColumnInfo(name = "vote_count") val voteCount: Int? = null,

    @SerializedName("video")
    @Expose
    @ColumnInfo(name = "video") val video: Boolean? = null,

    @SerializedName("poster_path")
    @Expose
    @ColumnInfo(name = "poster_path") val posterPath: String? = null,

    @SerializedName("id")
    @Expose
    @PrimaryKey val id: Int? = null,

    @SerializedName("adult")
    @Expose
    @ColumnInfo(name = "adult") val adult: Boolean? = null,

    @SerializedName("backdrop_path")
    @Expose
    @ColumnInfo(name = "backdrop_path") val backdropPath: String? = null,

    @SerializedName("original_language")
    @Expose
    @ColumnInfo(name = "original_language") val originalLanguage: String? = null,

    @SerializedName("original_title")
    @Expose
    @ColumnInfo(name = "original_title") val originalTitle: String? = null,

    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title") val title: String? = null,

    @SerializedName("vote_average")
    @Expose
    @ColumnInfo(name = "vote_average") val voteAverage: Double? = null,

    @SerializedName("overview")
    @Expose
    @ColumnInfo(name = "overview") val overview: String? = null,

    @SerializedName("release_date")
    @Expose
    @ColumnInfo(name = "release_date") val releaseDate: String? = null
)
