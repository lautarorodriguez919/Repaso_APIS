package com.example.apilistapp.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.compose.AsyncImage
import com.example.apilistapp.domain.SWCharacter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    bookId: String,
    navigateBack: () -> Unit
) {
    val vm: DetailViewModel = viewModel(
        key = bookId,
        factory = viewModelFactory {
            initializer { DetailViewModel(bookId) }
        }
    )
    val state by vm.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        vm.refreshFavoriteStatus()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.book?.title ?: "Detall",
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Enrere"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { vm.toggleFavorite() }) {
                        Icon(
                            imageVector = if (state.isFavorite)
                                Icons.Default.Favorite
                            else
                                Icons.Default.FavoriteBorder,
                            contentDescription = "Favorit",
                            tint = if (state.isFavorite)
                                MaterialTheme.colorScheme.error
                            else
                                MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                state.isLoading ->
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                state.error != null ->
                    Text(
                        text = state.error!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                state.book != null ->
                    BookDetailContent(
                        book = state.book!!,
                        isFavorite = state.isFavorite,
                        onToggleFavorite = { vm.toggleFavorite() }
                    )
            }
        }
    }
}

@Composable
private fun BookDetailContent(
    book: SWCharacter,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = book.thumbnail.ifBlank { null },
            contentDescription = book.title,
            modifier = Modifier
                .size(160.dp, 230.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = book.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 26.sp
        )

        if (book.authors.isNotEmpty()) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = book.authors.joinToString(", "),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            if (book.publishedDate.isNotBlank())
                SuggestionChip(
                    onClick = {},
                    label = { Text(book.publishedDate.take(4)) }
                )
            if (book.pageCount > 0)
                SuggestionChip(
                    onClick = {},
                    label = { Text("${book.pageCount} pàg.") }
                )
            if (book.language.isNotBlank())
                SuggestionChip(
                    onClick = {},
                    label = { Text(book.language.uppercase()) }
                )
        }

        if (book.averageRating > 0.0) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = "⭐ ${"%.1f".format(book.averageRating)} / 5",
                fontSize = 13.sp
            )
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onToggleFavorite,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isFavorite)
                    MaterialTheme.colorScheme.error
                else
                    MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = if (isFavorite)
                    Icons.Default.Favorite
                else
                    Icons.Default.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(if (isFavorite) "Treure de favorits" else "Afegir a favorits")
        }

        Spacer(Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(Modifier.height(16.dp))

        Text(
            text = book.description,
            fontSize = 14.sp,
            lineHeight = 22.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )

        if (book.categories.isNotEmpty()) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = "Categoria: ${book.categories.joinToString(", ")}",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        if (book.publisher.isNotBlank()) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Editorial: ${book.publisher}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }

        Spacer(Modifier.height(32.dp))
    }
}
