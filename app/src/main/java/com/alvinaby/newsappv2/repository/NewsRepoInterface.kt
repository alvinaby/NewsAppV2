package com.alvinaby.newsappv2.repository

import com.alvinaby.newsappv2.model.Responses
import io.reactivex.Observable

interface NewsRepoInterface {
    fun getNews(): Observable<Responses>
}
