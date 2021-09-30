package com.alvinaby.newsappv2.data.api

import com.alvinaby.newsappv2.model.Responses
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("top-headlines")
    fun getNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Observable<Responses>
}
