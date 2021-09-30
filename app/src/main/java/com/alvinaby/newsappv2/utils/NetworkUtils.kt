package com.alvinaby.newsappv2.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.alvinaby.newsappv2.view.ViewInterface

class NetworkUtils(private val view: ViewInterface): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        view.onNetworkChanged(isConnected(context!!))
    }

    @Suppress("DEPRECATION")
    companion object {
        fun isConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}
