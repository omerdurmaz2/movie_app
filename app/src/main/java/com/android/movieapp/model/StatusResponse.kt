package com.android.movieapp.model

import com.google.gson.annotations.SerializedName


class StatusResponse{
    @SerializedName("status_code")
    var statusCode: Int = -1

    @SerializedName("status_message")
    lateinit var statusMessage: String

    @SerializedName("success")
    var success: Boolean = false
}