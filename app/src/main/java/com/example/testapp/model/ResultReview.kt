package com.example.testapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResultReview(
    @SerializedName("author") @Expose
    private var author: String? = null,
    @SerializedName("content")
    @Expose
    private val content: String? = null,

    @SerializedName("id")
    @Expose
    private val id: String? = null,

    @SerializedName("url")
    @Expose
    private val url: String? = null
)
