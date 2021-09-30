package com.alvinaby.newsappv2.repository

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.alvinaby.newsappv2.BuildConfig
import com.alvinaby.newsappv2.data.api.ApiInterface
import com.alvinaby.newsappv2.model.Responses
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsRepo @Inject constructor (
    private val context: Context,
    private val apiInterface: ApiInterface
) : NewsRepoInterface {

    override fun getNews(): Observable<Responses> {
        val toast = Toast.makeText(context, "Loading news", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()

        return apiInterface.getNews("id", BuildConfig.NewsApiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
