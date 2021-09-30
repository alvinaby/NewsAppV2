package com.alvinaby.newsappv2.view

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alvinaby.newsappv2.databinding.NewsItemBinding
import com.alvinaby.newsappv2.model.Articles

class NewsAdapter(private val articles: List<Articles>) : RecyclerView.Adapter<NewsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): NewsViewHolder {
        return NewsViewHolder(
            NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int = articles.size
}
