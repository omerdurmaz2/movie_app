package com.android.movieapp.service

import android.content.Context
import android.net.ConnectivityManager
import com.android.movieapp.MovieApp
import com.android.movieapp.service.factories.MovieFactory
import com.android.movieapp.util.Constants
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
class RestControllerFactory() {

    private var movieFactory: MovieFactory

    init {

        val chuckerCollector = ChuckerCollector(
            context = MovieApp.getApplicationContext(),
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        val chuckerInterceptor = ChuckerInterceptor.Builder(MovieApp.getApplicationContext())
            .collector(chuckerCollector)
            .alwaysReadResponseBody(true)
            .build()

        val okHttpClient = OkHttpClient.Builder().addInterceptor(chuckerInterceptor)
            .connectTimeout(timeoutInterval, TimeUnit.SECONDS)
            .readTimeout(timeoutInterval, TimeUnit.SECONDS)

        client = okHttpClient.build()

        val apiService: Retrofit = Retrofit.Builder().baseUrl(Constants.Server.baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()

        movieFactory = apiService.create(MovieFactory::class.java)
    }

    companion object {
        const val timeoutInterval = 300L
        var client = OkHttpClient()

    }

    fun getMovieFactory(): MovieFactory {
        return movieFactory
    }
}