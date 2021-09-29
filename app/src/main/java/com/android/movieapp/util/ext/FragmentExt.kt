package com.android.movieapp.util.ext

import android.widget.Toast
import androidx.fragment.app.Fragment


fun Fragment.showToast(
    message: CharSequence,
    duration: Int = Toast.LENGTH_SHORT
) = activity?.showToast(message, duration)



