package com.example.newsapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapplication.R

@Composable
fun NewsListScreen(viewModel: NewsViewModel, onArticleClick: (Article) -> Unit) {
    var query by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxWidth().padding(20.dp),
        contentAlignment = Alignment.TopCenter
    ){
        Text(text = "Top Headlines", Modifier.size(300.dp), fontWeight = FontWeight.Bold)
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp)
    ){
        SearchBar(query, onQueryChange = {
            query = it
        }, onSearch = {
            viewModel.resetPagination()
            viewModel.getTopHeadlines(query)
        })

        val articles = viewModel.newsState.collectAsState().value

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(articles.size) { index ->
                NewsItem(article = articles[index],  onClick = { onArticleClick(articles[index]) })

                if (index == articles.size - 1) {
                    LaunchedEffect(Unit) {
                        viewModel.getTopHeadlines(query)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, onSearch: () -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        placeholder = { Text("Search news...") },
        trailingIcon = {
            IconButton(onClick = onSearch) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        },
        singleLine = true
    )
}

