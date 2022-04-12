package com.alvinaby.newsappv2.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.alvinaby.newsappv2.view.ActivityViewInterface

class NetworkUtils(private val activityView: ActivityViewInterface): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        activityView.onNetworkChanged(isConnected(context!!))
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
