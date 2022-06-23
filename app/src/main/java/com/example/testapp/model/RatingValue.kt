package com.example.testapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RatingValue(
    @SerializedName("value")
    @Expose
    var value: Double? = null
)
