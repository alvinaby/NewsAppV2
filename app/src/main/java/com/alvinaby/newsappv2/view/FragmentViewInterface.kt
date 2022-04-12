package com.alvinaby.newsappv2.view

import com.alvinaby.newsappv2.model.Responses

interface FragmentViewInterface {
    fun onSuccess(responses: Responses)
    fun onError()
}
