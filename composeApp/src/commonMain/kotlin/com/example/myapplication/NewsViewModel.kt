package com.example.myapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class NewsViewModel : ViewModel() {
    private val repository = NewsRepository()

    // 4. STATEFLOW (20%) - State Management
    private val _selectedCategory = MutableStateFlow("Tech")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _readCount = MutableStateFlow(0)
    val readCount: StateFlow<Int> = _readCount.asStateFlow()

    // Menggunakan flatMapLatest agar flow restart setiap kategori berubah
    @OptIn(ExperimentalCoroutinesApi::class)
    val newsFeed: Flow<News> = _selectedCategory.flatMapLatest { category ->
        repository.getNewsFeed(category)
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    fun incrementReadCount() {
        _readCount.value += 1
    }
}