package com.nutrimind.ai.presentation.screens.analytics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nutrimind.ai.presentation.components.BarChart
import com.nutrimind.ai.presentation.components.NutriCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Analytics", fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                NutriCard {
                    Column {
                        Text("Weekly Calorie Intake", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        BarChart(data = listOf(1800f, 2200f, 2000f, 2500f, 1900f, 2100f, 2300f))
                    }
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    NutriCard(modifier = Modifier.weight(1f)) {
                        Text("Avg Calories", style = MaterialTheme.typography.labelSmall)
                        Text("2,114", fontSize = 20.sp, fontWeight = FontWeight.Black)
                    }
                    NutriCard(modifier = Modifier.weight(1f)) {
                        Text("Weight Change", style = MaterialTheme.typography.labelSmall)
                        Text("-1.2 kg", fontSize = 20.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
            
            item {
                NutriCard {
                    Column {
                        Text("Macro Distribution", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        // Add Pie Chart or Progress Bars here
                        MacroRow("Carbs", 0.45f, MaterialTheme.colorScheme.primary)
                        MacroRow("Protein", 0.30f, MaterialTheme.colorScheme.secondary)
                        MacroRow("Fats", 0.25f, MaterialTheme.colorScheme.tertiary)
                    }
                }
            }
        }
    }
}

@Composable
fun MacroRow(label: String, percentage: Float, color: androidx.compose.ui.graphics.Color) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, style = MaterialTheme.typography.bodySmall)
            Text("${(percentage * 100).toInt()}%", style = MaterialTheme.typography.bodySmall)
        }
        LinearProgressIndicator(
            progress = { percentage },
            modifier = Modifier.fillMaxWidth().height(8.dp),
            color = color,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
        )
    }
}
