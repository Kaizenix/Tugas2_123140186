package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val viewModel: NewsViewModel = viewModel { NewsViewModel() }
    val readCount by viewModel.readCount.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val newsList = remember { mutableStateListOf<News>() }

    LaunchedEffect(selectedCategory) {
        newsList.clear()
        viewModel.newsFeed.collect { news ->
            newsList.add(0, news)
        }
    }

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF2E7D32),
            surface = Color.White,
            secondaryContainer = Color(0xFFE8F5E9)
        )
    ) {
        Scaffold(
            topBar = { NewsHeader() }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(Color(0xFFF1F8E9))
            ) {
                StatsAndFilters(
                    readCount = readCount,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { viewModel.setCategory(it) }
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(newsList) { news ->
                        NewsCard(
                            news = news,
                            onRead = { viewModel.incrementReadCount() }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsHeader() {
    CenterAlignedTopAppBar(
        title = { Text("News Simulator", fontWeight = FontWeight.ExtraBold) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsAndFilters(
    readCount: Int,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 1.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Berita dibaca: $readCount",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Tech", "Sports", "Politics").forEach { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { onCategorySelected(category) },
                        label = { Text(category) },
                        shape = RoundedCornerShape(20.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun NewsCard(news: News, onRead: () -> Unit) {
    var isClicked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { 
                if (!isClicked) {
                    isClicked = true
                    onRead()
                }
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        news.category, 
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    if (isClicked) "Sudah Dibaca" else "Baru", 
                    fontSize = 11.sp, 
                    fontWeight = FontWeight.Bold,
                    color = if (isClicked) Color.Gray else Color(0xFFD32F2F)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                news.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                news.content,
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray,
                lineHeight = 18.sp
            )
        }
    }
}
