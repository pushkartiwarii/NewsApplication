package com.example.newsapplication.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class Article(
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?
)

data class NewsResponse(
    val articles: List<Article>
)

class NewsViewModel : ViewModel() {
    private val _newsState = MutableStateFlow<List<Article>>(emptyList())
    val newsState: StateFlow<List<Article>> = _newsState

    private var currentPage = 1
    private var currentQuery: String? = null
    private var isLoading = false

    init {
        getTopHeadlines()
    }

    fun getTopHeadlines(query: String? = null) {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            currentQuery = query
            val response = if (query.isNullOrEmpty()) {
                RetrofitInstance.api.getTopHeadlines("us", "ca4d8c082f53438db9fd24ac9014511f", page = currentPage)
            } else {
                RetrofitInstance.api.searchNews(query, "ca4d8c082f53438db9fd24ac9014511f", page = currentPage)
            }
            _newsState.update { currentArticles ->
                currentArticles + response.articles
            }
            currentPage++
            isLoading = false
        }
    }

    fun resetPagination() {
        currentPage = 1
        _newsState.value = emptyList()
        getTopHeadlines(currentQuery)
    }
}