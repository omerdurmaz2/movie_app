package com.android.movieapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApp : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var mContext: Context
        fun getApplicationContext(): Context {
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        initializeApi()
    }

    private fun initializeApi() {
        mContext = applicationContext
    }

}