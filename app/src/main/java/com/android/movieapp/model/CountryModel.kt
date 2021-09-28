package com.android.movieapp.model

import com.google.gson.annotations.SerializedName


data class CountryModel (

	@SerializedName("iso_3166_1") val iso_3166_1 : String,
	@SerializedName("name") val name : String
)