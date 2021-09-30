package com.alvinaby.newsappv2.presenter

import com.alvinaby.newsappv2.repository.NewsRepo
import com.alvinaby.newsappv2.view.ViewInterface
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class NewsPresenter @Inject constructor (
    private val view: ViewInterface,
    private val newsRepo: NewsRepo
) : NewsPresenterInterface {

    private var disposable: Disposable? = null

    override fun loadNews() {
         disposable = newsRepo.getNews().subscribe(
             { view.onSuccess(it) },
             { view.onError() }
         )
    }

    override fun disposeNews() {
        disposable?.dispose()
    }

}
