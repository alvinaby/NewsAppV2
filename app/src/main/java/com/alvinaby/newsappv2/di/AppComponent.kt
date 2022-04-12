package com.alvinaby.newsappv2.di

import com.alvinaby.newsappv2.fragment.HomeFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(homeFragment: HomeFragment)
}
