package com.moviewers.testapp.testapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RateMovie(
    @SerializedName("status_code") @Expose
    var status_code: Int? = null,

    @SerializedName("status_message")
    @Expose
    val status_message: String
)
