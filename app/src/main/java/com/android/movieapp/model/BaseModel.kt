package com.android.movieapp.model

import com.google.gson.annotations.SerializedName

data class BaseModel(
    @SerializedName("dates") val dates: DateRangeModel,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieItemModel>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)