package com.nutrimind.ai.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nutrimind.ai.ui.theme.Lavender
import com.nutrimind.ai.ui.theme.Peach

@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    var currentPage by remember { mutableStateOf(0) }
    val pages = listOf(
        OnboardingPage("NutriMind AI", "Your personal AI nutrition companion for a healthier life.", Lavender),
        OnboardingPage("Smart Scanning", "Scan products and ingredients instantly with AI analysis.", Peach),
        OnboardingPage("Goal Tracking", "Track calories, macros, and water intake with ease.", Color(0xFFE3F2FD))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(pages[currentPage].backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = pages[currentPage].title,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = pages[currentPage].description,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = Color.DarkGray
            )
        }

        Button(
            onClick = {
                if (currentPage < pages.size - 1) {
                    currentPage++
                } else {
                    onFinished()
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(32.dp)
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(if (currentPage == pages.size - 1) "Get Started" else "Next")
        }
    }
}

data class OnboardingPage(
    val title: String,
    val description: String,
    val backgroundColor: Color
)
