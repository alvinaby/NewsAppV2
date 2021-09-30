package com.alvinaby.newsappv2.view

import com.alvinaby.newsappv2.model.Articles
import com.alvinaby.newsappv2.model.Responses

interface ViewInterface {
    fun onSuccess(responses: Responses)
    fun onError()
    fun onNetworkChanged(isConnected: Boolean)
    fun openNews(url: String)
}