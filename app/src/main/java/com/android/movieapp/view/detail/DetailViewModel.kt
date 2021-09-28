package com.android.movieapp.view.detail

import androidx.lifecycle.ViewModel
import com.android.movieapp.base.DataState
import com.android.movieapp.model.BaseModel
import com.android.movieapp.model.MovieDetailModel
import com.android.movieapp.service.NetworkCallback
import com.android.movieapp.service.RestControllerFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val restController: RestControllerFactory
) : ViewModel() {

    var detail: MovieDetailModel? = null

    fun getDetail(callback: (DataState) -> Unit) {

        restController.getMovieFactory().getMovieDetail(550).enqueue(object :
            NetworkCallback<MovieDetailModel>() {
            override fun onResponse(
                call: Call<MovieDetailModel>,
                response: Response<MovieDetailModel>
            ) {
                super.onResponse(call, response)
                if (response.isSuccessful) {
                    detail = response.body()
                    callback(DataState.Success(""))
                } else {
                    callback.invoke(DataState.Error(""))
                }
            }

            override fun onFailure(
                call: Call<MovieDetailModel>,
                t: Throwable

            ) {
                super.onFailure(call, t)
                callback.invoke(DataState.Error(""))
            }
        })

    }
}