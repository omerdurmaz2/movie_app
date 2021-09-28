package com.android.movieapp.service.factories

import com.android.movieapp.model.BaseModel
import com.android.movieapp.model.MovieDetailModel
import com.android.movieapp.util.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieFactory {


    @GET("movie/{id}")
    fun getMovieDetail(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = Constants.Server.apiKey,
    ): Call<MovieDetailModel>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = Constants.Server.apiKey,
        @Query("page") page: Int? = null,
    ): Call<BaseModel>

    @GET("movie/now_playing")
    fun getPlayingMovies(
        @Query("api_key") apiKey: String = Constants.Server.apiKey,
    ): Call<BaseModel>


}