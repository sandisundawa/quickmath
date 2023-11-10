package com.moviewers.testapp.testapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TrendingResult(
    @SerializedName("page") @Expose
    var page: Int? = null,

    @SerializedName("results")
    @Expose
    val trending: List<Result>
)
