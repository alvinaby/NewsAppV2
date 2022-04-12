package com.alvinaby.newsappv2.di

import android.content.Context
import com.alvinaby.newsappv2.data.api.ApiInterface
import com.alvinaby.newsappv2.data.api.ApiService
import com.alvinaby.newsappv2.view.FragmentViewInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val context: Context, val view: FragmentViewInterface) {
    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideApiInterface(): ApiInterface = ApiService().getApi()

    @Provides
    @Singleton
    fun provideView(): FragmentViewInterface = view
}
