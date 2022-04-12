package com.alvinaby.newsappv2.view

interface ActivityViewInterface {
    fun openNews(url: String)
    fun onNetworkChanged(isConnected: Boolean)
}
