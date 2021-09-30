package com.alvinaby.newsappv2.model

data class Responses(
    var status: String? = null,
    var totalResults: Int? = null,
    var articles: List<Articles>? = null
)
