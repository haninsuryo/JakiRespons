package com.example.jakirespons.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(@StringRes res: Int, @IdRes anchor: Int) {
    Snackbar.make(this.context, this, resources.getString(res), Snackbar.LENGTH_SHORT)
        .setAnchorView(anchor)
        .show()
}

fun View.showSnackbar(@StringRes res: Int) {
    Snackbar.make(this.context, this, resources.getString(res), Snackbar.LENGTH_SHORT)
        .show()
}

fun Context.isConnected(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    var result = false
    if (activeNetwork != null) {
        result = activeNetwork.isConnectedOrConnecting
    }
    return result
}