package com.nutrimind.ai.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nutrimind.ai.domain.model.MealType
import com.nutrimind.ai.presentation.components.NutriCard
import com.nutrimind.ai.presentation.viewmodel.TrackerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodScreen(
    onNavigateBack: () -> Unit,
    viewModel: TrackerViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf("") }
    val searchResults by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var selectedMealType by remember { mutableStateOf(MealType.BREAKFAST) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Food", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        // Icon(Icons.Default.ArrowBack, contentDescription = null)
                        Text("←")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Voice Intent */ }) {
                        Icon(Icons.Default.Mic, contentDescription = "Voice Input")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                MealType.entries.forEach { type ->
                    FilterChip(
                        selected = selectedMealType == type,
                        onClick = { selectedMealType = type },
                        label = { Text(type.name) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = query,
                onValueChange = { 
                    query = it
                    viewModel.searchFood(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search for food...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(searchResults) { food ->
                    NutriCard {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(food.name, fontWeight = FontWeight.Bold)
                                Text("${food.calories} kcal / 100g", style = MaterialTheme.typography.bodySmall)
                            }
                            Button(onClick = { 
                                viewModel.addFood(food, selectedMealType)
                                onNavigateBack()
                            }) {
                                Text("Add")
                            }
                        }
                    }
                }
            }
        }
    }
}
