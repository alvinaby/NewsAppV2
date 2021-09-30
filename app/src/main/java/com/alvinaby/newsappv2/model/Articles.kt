package com.alvinaby.newsappv2.model

import java.util.*

data class Articles(
    var source: Source? = null,
    var author: String? = null,
    var title: String? = null,
    var description: String? = null,
    var url: String? = null,
    var urlToImage: String? = null,
    var publishedAt: Date? = null,
    var content: String? = null
)
