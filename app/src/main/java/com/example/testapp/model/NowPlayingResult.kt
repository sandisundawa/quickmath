package com.example.testapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NowPlayingResult(
    @SerializedName("page") @Expose
    var page: Int? = null,

    @SerializedName("results")
    @Expose
    val nowPlaying: List<NowPlaying>
)