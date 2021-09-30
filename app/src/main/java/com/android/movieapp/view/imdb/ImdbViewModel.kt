package com.android.movieapp.view.imdb

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.movieapp.base.DataState
import com.android.movieapp.model.BaseModel
import com.android.movieapp.model.MovieDetailModel
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
class ImdbViewModel @Inject constructor(
    val gson: Gson
) : ViewModel() {

}