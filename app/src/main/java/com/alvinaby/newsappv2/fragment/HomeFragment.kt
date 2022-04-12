package com.alvinaby.newsappv2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvinaby.newsappv2.databinding.FragmentHomeBinding
import com.alvinaby.newsappv2.di.AppModule
import com.alvinaby.newsappv2.di.DaggerAppComponent
import com.alvinaby.newsappv2.model.Responses
import com.alvinaby.newsappv2.presenter.NewsPresenter
import com.alvinaby.newsappv2.view.FragmentViewInterface
import com.alvinaby.newsappv2.view.NewsAdapter
import javax.inject.Inject

class HomeFragment : Fragment(), FragmentViewInterface {

    @Inject lateinit var newsPresenter: NewsPresenter

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        //Load news list
        loadNews()

        //Refresh news list
        binding.refreshNews.setOnRefreshListener {
            loadNews()
            binding.refreshNews.isRefreshing = false
        }

        return binding.root
    }

    private fun loadNews() {
        val appComponent = DaggerAppComponent.builder()
            .appModule(activity?.let { AppModule(it, this) })
            .build()

        appComponent.inject(this)
        newsPresenter.loadNews()
    }

    override fun onDestroy() {
        super.onDestroy()
        newsPresenter.disposeNews()
    }

    override fun onSuccess(responses: Responses) {
        val bindView = binding.newsView
        val articles = responses.articles

        bindView.setHasFixedSize(true)
        bindView.layoutManager = LinearLayoutManager(activity)
        bindView.adapter = articles?.let { NewsAdapter(it) }
    }

    override fun onError() {
        Toast.makeText(activity, "No news found", Toast.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance() = HomeFragment().apply { HomeFragment().arguments = Bundle() }
    }
}
