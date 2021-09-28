package com.android.movieapp.service

import android.util.Log
import android.widget.Toast
import com.android.movieapp.MovieApp
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class NetworkCallback<T> : Callback<T> {


    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.code() != 200) {
            if (response.body() != null) {
                try {
                    val errorBody = JSONObject(response.errorBody()!!.toString())
                    Toast.makeText(
                        MovieApp.getApplicationContext(),
                        "Error: $errorBody",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Log.e("sss", "error: $e")
                }
            }
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        if (t.message!!.contains("Unable to resolve host")) {
            Toast.makeText(
                MovieApp.getApplicationContext(),
                "You do not have an internet connection",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                MovieApp.getApplicationContext(),
                t.localizedMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}