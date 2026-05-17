package com.nutrimind.ai.presentation.screens.scanner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nutrimind.ai.domain.model.FoodEntry
import com.nutrimind.ai.domain.model.FoodRating
import com.nutrimind.ai.domain.model.NutritionAnalysis
import com.nutrimind.ai.presentation.components.NutriCard

@Composable
fun ScanResultScreen(
    entry: FoodEntry?,
    analysis: NutritionAnalysis,
    onClose: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = entry?.name ?: "Analysis Result",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            val ratingColor = when(analysis.rating) {
                FoodRating.GOOD -> Color(0xFF4CAF50)
                FoodRating.BAD -> Color(0xFFFF9800)
                FoodRating.WORST -> Color(0xFFF44336)
            }
            
            NutriCard(containerColor = ratingColor.copy(alpha = 0.1f)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = analysis.rating.name,
                        color = ratingColor,
                        fontWeight = FontWeight.Black,
                        fontSize = 32.sp
                    )
                    Text("Health Score: ${analysis.healthScore}/100")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text("AI Analysis", fontWeight = FontWeight.Bold)
            Text(analysis.summary, style = MaterialTheme.typography.bodyMedium)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                item { Text("Highlights", fontWeight = FontWeight.SemiBold) }
                items(analysis.highlights) { Text("• $it") }
                
                item { Spacer(modifier = Modifier.height(8.dp)) }
                
                item { Text("Risks", fontWeight = FontWeight.SemiBold, color = Color.Red) }
                items(analysis.risks) { Text("• $it") }
                
                item { Spacer(modifier = Modifier.height(16.dp)) }
                
                item {
                    Button(
                        onClick = onClose,
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Done")
                    }
                }
            }
        }
    }
}
