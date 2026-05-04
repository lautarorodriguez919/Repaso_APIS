package com.example.apilistapp.ui.screens.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.apilistapp.domain.SWCharacter
import com.example.apilistapp.ui.screens.list.ShowMode

@Composable
fun FavoritesScreen(
    navigateToDetail: (String) -> Unit,
    showMode: ShowMode = ShowMode.LIST,
    vm: FavoritesViewModel = viewModel()
) {
    val favorites by vm.favorites.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        vm.loadFavorites()
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            text = "Favorits",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                //Aqui mis consultas
                Text(
                    text = "se ha de mostrar le nom del personaje centrado en negreta i con tamaño de funeta mas grande que el resto de texto encima esta una cita i la imagen del personaje, que es una al lado de la otra" +
                            "en funciuon de ek atrbuto character direction tendras que poner la imagena ntes o despeus del texto" +
                            "eso seria el random quotre dahora te paso la seccino de favorites",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        } else {
            if (showMode == ShowMode.LIST) {
                FavoritesLazyColumn(
                    books = favorites,
                    navigateToDetail = navigateToDetail,
                    onDelete = { vm.deleteFavorite(it) }
                )
            } else {
                FavoritesLazyGrid(
                    books = favorites,
                    navigateToDetail = navigateToDetail
                )
            }
        }
    }
}

@Composable
fun FavoritesLazyColumn(
    books: List<SWCharacter>,
    navigateToDetail: (String) -> Unit,
    onDelete: (SWCharacter) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(books, key = { it.id }) { book ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navigateToDetail(book.id) }
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = book.thumbnail.ifBlank { null },
                    contentDescription = book.title,
                    modifier = Modifier
                        .size(55.dp, 80.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = book.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (book.authors.isNotEmpty()) {
                        Text(
                            text = book.authors.joinToString(", "),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                IconButton(onClick = { onDelete(book) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
            HorizontalDivider()
        }
    }
}

@Composable
fun FavoritesLazyGrid(
    books: List<SWCharacter>,
    navigateToDetail: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(books, key = { it.id }) { book ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navigateToDetail(book.id) },
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(3.dp)
            ) {
                Column {
                    AsyncImage(
                        model = book.thumbnail.ifBlank { null },
                        contentDescription = book.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = book.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}
