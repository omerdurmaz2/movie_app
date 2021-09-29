package com.android.movieapp.view.home

import androidx.lifecycle.ViewModel
import com.android.movieapp.base.DataState
import com.android.movieapp.model.BaseModel
import com.android.movieapp.model.MovieItemModel
import com.android.movieapp.service.NetworkCallback
import com.android.movieapp.service.RestControllerFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val restController: RestControllerFactory
) : ViewModel() {

    var playingMovies: BaseModel? = null
    var baseUpcomingMovies: BaseModel? = null
    var upcomingMovies = ArrayList<MovieItemModel?>()
    var page = 1
    var isLoading = false
     lateinit var listAdapter: UpcomingMoviesAdapter

     private var isNavigated=false

    fun getPlayingMovies(callback: (DataState) -> Unit) {

        restController.getMovieFactory().getPlayingMovies().enqueue(object :
            NetworkCallback<BaseModel>() {
            override fun onResponse(
                call: Call<BaseModel>,
                response: Response<BaseModel>
            ) {
                super.onResponse(call, response)
                if (response.isSuccessful) {
                    playingMovies = response.body()
                    callback(DataState.Success(""))
                } else {
                    callback.invoke(DataState.Error(""))
                }
            }

            override fun onFailure(
                call: Call<BaseModel>,
                t: Throwable

            ) {
                super.onFailure(call, t)
                callback.invoke(DataState.Error(""))
            }
        })

    }

    fun getUpcomingMovies(callback: (DataState) -> Unit) {

        restController.getMovieFactory().getUpcomingMovies(page = page).enqueue(object :
            NetworkCallback<BaseModel>() {
            override fun onResponse(
                call: Call<BaseModel>,
                response: Response<BaseModel>
            ) {
                super.onResponse(call, response)
                if (response.isSuccessful) {
                    baseUpcomingMovies = response.body()

                    if (upcomingMovies.isNotEmpty()) upcomingMovies.removeAt(upcomingMovies.size - 1)

                    response.body()?.results?.let { upcomingMovies.addAll(it) }
                    callback(DataState.Success(""))
                } else {
                    callback.invoke(DataState.Error(""))
                }
            }

            override fun onFailure(
                call: Call<BaseModel>,
                t: Throwable

            ) {
                super.onFailure(call, t)
                callback.invoke(DataState.Error(""))
            }
        })

    }
}