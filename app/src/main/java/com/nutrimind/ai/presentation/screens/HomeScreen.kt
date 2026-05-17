package com.nutrimind.ai.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nutrimind.ai.presentation.components.DailyProgressRing
import com.nutrimind.ai.presentation.components.NutriCard
import com.nutrimind.ai.presentation.viewmodel.HomeViewModel
import com.nutrimind.ai.ui.theme.Lavender
import com.nutrimind.ai.ui.theme.SoftBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddFood: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hello, NutriMind", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Notifications, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddFood,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = MaterialTheme.shapes.large
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Food")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                NutriCard(containerColor = Lavender) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Today", style = MaterialTheme.typography.titleMedium)
                            Text(
                                "${uiState.consumedCalories} / ${uiState.targetCalories}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Black
                            )
                            Text("kcal consumed", style = MaterialTheme.typography.bodySmall)
                        }
                        DailyProgressRing(
                            progress = (uiState.consumedCalories.toFloat() / uiState.targetCalories.coerceAtLeast(1)),
                            modifier = Modifier.size(100.dp)
                        ) {
                            Text(
                                "${((uiState.consumedCalories.toFloat() / uiState.targetCalories.coerceAtLeast(1)) * 100).toInt()}%",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    NutriCard(
                        modifier = Modifier.weight(1f),
                        containerColor = SoftBlue
                    ) {
                        Icon(Icons.Default.WaterDrop, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Water", fontWeight = FontWeight.Bold)
                        Text("${uiState.waterIntake} / 2000 ml", style = MaterialTheme.typography.bodySmall)
                    }
                    NutriCard(
                        modifier = Modifier.weight(1f),
                        containerColor = Color(0xFFFFE0E0) // Soft Red
                    ) {
                        Text("🔥", fontSize = 24.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Streak", fontWeight = FontWeight.Bold)
                        Text("7 Days", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            item {
                NutriCard(containerColor = Color(0xFFE8F5E9)) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("✨ AI Suggestion", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "You are doing great! Try to add more protein to your breakfast to stay full longer.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            item {
                Text("Recent Meals", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }

            items(uiState.dailyEntries) { entry ->
                MealItem(entry.name, entry.calories, entry.mealType.name)
            }
            
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun MealItem(name: String, calories: Int, type: String) {
    NutriCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(name, fontWeight = FontWeight.Bold)
                Text(type, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }
            Text("$calories kcal", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
        }
    }
}
