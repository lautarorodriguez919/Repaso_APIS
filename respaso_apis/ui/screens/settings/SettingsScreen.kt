package com.example.apilistapp.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apilistapp.ui.screens.list.ShowMode

@Composable
fun SettingsScreen(
    onDarkModeChange: (Boolean) -> Unit = {},
    onShowModeChange: (ShowMode) -> Unit = {},
    vm: SettingsViewModel = viewModel()
) {
    val state by vm.state.collectAsStateWithLifecycle()
    var dropdownExpanded by remember { mutableStateOf(false) }

    if (state.showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { vm.dismissDeleteDialog() },
            title = { Text("Eliminar favorits") },
            text = {
                Text("Estàs segur que vols eliminar tots els favorits? Aquesta acció no es pot desfer.")
            },
            confirmButton = {
                TextButton(onClick = { vm.deleteAllFavorites() }) {
                    Text(
                        text = "Eliminar",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { vm.dismissDeleteDialog() }) {
                    Text("Cancel·lar")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Configuració",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        HorizontalDivider()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Dark Mode", fontSize = 16.sp)
            Switch(
                checked = state.isDarkMode,
                onCheckedChange = {
                    vm.toggleDarkMode()
                    onDarkModeChange(it)
                }
            )
        }

        HorizontalDivider()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Show Mode", fontSize = 16.sp)
            Box {
                OutlinedButton(onClick = { dropdownExpanded = true }) {
                    Text(
                        text = if (state.showMode == ShowMode.LIST) "List"
                        else "Grid"
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("List") },
                        onClick = {
                            vm.setShowMode(ShowMode.LIST)
                            onShowModeChange(ShowMode.LIST)
                            dropdownExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Grid") },
                        onClick = {
                            vm.setShowMode(ShowMode.GRID)
                            onShowModeChange(ShowMode.GRID)
                            dropdownExpanded = false
                        }
                    )
                }
            }
        }

        HorizontalDivider()

        Button(
            onClick = { vm.showDeleteDialog() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text(
                text = "Delete favs",
                color = MaterialTheme.colorScheme.onError
            )
        }
    }
}
