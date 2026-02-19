package com.example.myapplication

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

data class News(
    val id: Int,
    val title: String,
    val category: String,
    val content: String
)

class NewsRepository {
    private val allNews = listOf(
        News(1, "Breaking: AI Revolution", "Tech", "AI is changing everything."),
        News(2, "Soccer Finals Tonight", "Sports", "The stadium is ready."),
        News(3, "New Gadget Launch", "Tech", "The latest smartphone is here."),
        News(4, "Election Update", "Politics", "Votes are being counted."),
        News(5, "Tennis Open Result", "Sports", "A new champion emerges."),
        News(6, "Tech Summit 2024", "Tech", "Silicon Valley news.")
    )

    // 1. FLOW BUILDER & EMIT (25%)
    fun getNewsFeed(selectedCategory: String): Flow<News> = flow {
        allNews.forEach { news ->
            emit(news) // Mengirim data satu per satu
            delay(2000) // Simulasi data muncul setiap 2 detik
        }
    }
        // 2. FLOW OPERATORS (20%) - Filter dilakukan di Flow, bukan di UI
        .onEach { println("Logging: Mendapatkan berita ${it.title}") } // onEach untuk logging
        .filter { it.category == selectedCategory || selectedCategory == "All" } // filter kategori
        .map { news ->
            // Transformasi data: Menambahkan detail secara asinkron
            val detail = fetchNewsDetail(news.id)
            news.copy(content = "${news.content} | Detail: $detail")
        }

    // 3. COROUTINES ASYNC/AWAIT (20%)
    private suspend fun fetchNewsDetail(id: Int): String = coroutineScope {
        val deferredDetail = async(Dispatchers.Default) {
            delay(500) // Simulasi network call
            "Informasi tambahan untuk berita #$id (Verified)"
        }
        deferredDetail.await() // Menggunakan await untuk mendapatkan hasil
    }
}