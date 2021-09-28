package com.android.movieapp.model

import com.google.gson.annotations.SerializedName


data class DateRangeModel(
    @SerializedName("maximum") val maximum: String,
    @SerializedName("minimum") val minimum: String
)