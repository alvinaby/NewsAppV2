package com.alvinaby.newsappv2.repository

import com.alvinaby.newsappv2.BuildConfig
import com.alvinaby.newsappv2.data.api.ApiInterface
import com.alvinaby.newsappv2.model.Responses
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsRepo @Inject constructor (private val apiInterface: ApiInterface) : NewsRepoInterface {

    override fun getNews(country: String): Observable<Responses> =
        apiInterface.getNews(country, BuildConfig.NewsApiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}
