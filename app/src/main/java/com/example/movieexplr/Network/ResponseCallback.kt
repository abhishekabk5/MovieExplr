package com.example.movieexplr.Network

import android.util.Log
import com.android.volley.VolleyError

abstract class ResponseCallback<T> {

    abstract fun onSuccess(response: T)

    fun onError(error: VolleyError) {
        Log.d("API Call Error", "")
        error.printStackTrace()
    }
}
