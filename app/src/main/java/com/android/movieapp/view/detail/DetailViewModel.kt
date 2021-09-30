package com.android.movieapp.view.detail

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.movieapp.base.DataState
import com.android.movieapp.model.BaseModel
import com.android.movieapp.model.MovieDetailModel
import com.android.movieapp.model.MovieItemModel
import com.android.movieapp.service.NetworkCallback
import com.android.movieapp.service.RestControllerFactory
import com.android.movieapp.util.DateUtils
import com.android.movieapp.view.MainActivity
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val restController: RestControllerFactory,
    val gson: Gson
) : ViewModel() {


    var selectedItem: MovieItemModel? = null
    val detail = ObservableField<MovieDetailModel>()
    val releaseDate = ObservableField<String>()
    val year = ObservableField<String>()
    fun getDetail(callback: (DataState) -> Unit) {

        selectedItem?.id?.let {
            restController.getMovieFactory().getMovieDetail(it)
                .enqueue(object :
                    NetworkCallback<MovieDetailModel>() {
                    override fun onResponse(
                        call: Call<MovieDetailModel>,
                        response: Response<MovieDetailModel>
                    ) {
                        super.onResponse(call, response)
                        if (response.isSuccessful) {
                            detail.set(response.body())
                            releaseDate.set(DateUtils.convertApiDateToAppDate(response.body()?.releaseDate))
                            year.set(DateUtils.convertApiDateToYear(response.body()?.releaseDate))
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
}