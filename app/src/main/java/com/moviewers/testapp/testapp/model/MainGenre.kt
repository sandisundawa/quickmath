package com.moviewers.testapp.testapp.model

import com.google.gson.annotations.SerializedName

data class MainGenre(
    @SerializedName("genres")
    var genres: List<Genre>
)