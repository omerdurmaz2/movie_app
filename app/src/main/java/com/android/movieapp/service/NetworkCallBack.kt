package com.android.movieapp.service

import android.util.Log
import android.widget.Toast
import com.android.movieapp.MovieApp
import com.android.movieapp.R
import com.android.movieapp.model.StatusResponse
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess

abstract class NetworkCallback<T> : Callback<T> {


    override fun onResponse(call: Call<T>, response: Response<T>) {

        if (response.code() != 200) {

            val message = when (response.code()) {
                401 -> {
                    "401 - Unauthorized"
                }
                404 -> {
                    convertToStatusResponse(response.body())
                }
                else -> ""
            }
            Toast.makeText(
                MovieApp.getApplicationContext(),
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun convertToStatusResponse(response: T?): String {
        return try {
            if (response != null) {
                val gson = Gson()
                val statusResponse = gson.fromJson(
                    response.toString(),
                    StatusResponse::class.java
                )
                "${statusResponse.statusCode} - ${statusResponse.statusMessage}"
            } else MovieApp.getApplicationContext().getString(R.string.technical_error_message)
        } catch (e: Exception) {
            ""
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
        exitProcess(0)
    }
}