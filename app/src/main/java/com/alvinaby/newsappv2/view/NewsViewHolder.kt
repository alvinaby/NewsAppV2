package com.alvinaby.newsappv2.view

import androidx.recyclerview.widget.RecyclerView
import com.alvinaby.newsappv2.databinding.NewsItemBinding
import com.alvinaby.newsappv2.model.Articles
import com.bumptech.glide.Glide

class NewsViewHolder(private val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(articles: Articles) {
        // Image
        Glide.with(itemView)
            .load(articles.urlToImage)
            .centerCrop()
            .into(binding.newsImage)

        // Title
        binding.newsTitle.text = articles.title ?: "No title"

        // Source
        binding.newsSource.text = articles.source?.name ?: "No source"

        // Date published
        binding.datePublished.text = articles.publishedAt.toString()

        // Open News
        itemView.setOnClickListener {
            articles.url?.let { (itemView.context as ViewInterface).openNews(it) }
        }
    }
}
