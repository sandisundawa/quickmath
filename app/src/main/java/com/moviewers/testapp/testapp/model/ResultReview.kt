package com.moviewers.testapp.testapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResultReview(
    @SerializedName("author") @Expose
    var author: String? = null,
    @SerializedName("content")
    @Expose
    val content: String? = null,

    @SerializedName("id")
    @Expose
    val id: String? = null,

    @SerializedName("url")
    @Expose
    val url: String? = null
)
