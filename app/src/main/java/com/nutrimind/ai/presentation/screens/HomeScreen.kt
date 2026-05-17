package com.nutrimind.ai.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.nutrimind.ai.presentation.components.BarChart
import com.nutrimind.ai.presentation.components.DailyProgressRing
import com.nutrimind.ai.presentation.components.NutriCard
import com.nutrimind.ai.presentation.viewmodel.HomeViewModel
import com.nutrimind.ai.ui.theme.Lavender
import com.nutrimind.ai.ui.theme.SoftBlue
import com.nutrimind.ai.ui.theme.MintGreen
import com.nutrimind.ai.ui.theme.Peach

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
                title = { 
                    Column {
                        Text("Hello, NutriMind", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text("Ready to track your day?", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Notifications, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddFood,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = RoundedCornerShape(20.dp),
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Log Food") }
            )
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
                            Text("Calorie Budget", style = MaterialTheme.typography.titleSmall, color = Color.DarkGray)
                            Text(
                                "${uiState.consumedCalories} / ${uiState.targetCalories}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Black
                            )
                            Text("kcal consumed today", style = MaterialTheme.typography.labelMedium)
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
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        MacroItem("Protein", "${uiState.consumedProtein}g", Color(0xFF64B5F6))
                        MacroItem("Carbs", "${uiState.consumedCarbs}g", Color(0xFFFFB74D))
                        MacroItem("Fats", "${uiState.consumedFats}g", Color(0xFF81C784))
                    }
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    NutriCard(
                        modifier = Modifier.weight(1f),
                        containerColor = SoftBlue
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.WaterDrop, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Water", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("${uiState.waterIntake} ml", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Text("Goal: 2000 ml", style = MaterialTheme.typography.labelSmall)
                    }
                    NutriCard(
                        modifier = Modifier.weight(1f),
                        containerColor = Peach
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🔥", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Streak", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("7 Days", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Text("Keep it up!", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            item {
                NutriCard(containerColor = MintGreen) {
                    Column {
                        Text("✨ AI Health Insight", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "You're 200kcal below your target. Adding a protein-rich snack like greek yogurt could help you reach your goals.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            item {
                Text("Weekly Analytics", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }

            item {
                NutriCard {
                    Text("Calorie Trends", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    BarChart(data = uiState.weeklyCalories)
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Today's Meals", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    TextButton(onClick = {}) {
                        Text("See All")
                    }
                }
            }

            items(uiState.dailyEntries) { entry ->
                MealItem(entry.name, entry.calories, entry.mealType.name)
            }
            
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun MacroItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold, color = color)
        Text(label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
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
