package com.moviewers.testapp.testapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SessionResult(
    @SerializedName("success")
    @Expose
    var success: Boolean? = null,

    @SerializedName("guest_session_id")
    @Expose
    var guest_session_id: String? = null,

    @SerializedName("expires_at")
    @Expose
    var expires_at: String? = null
)
