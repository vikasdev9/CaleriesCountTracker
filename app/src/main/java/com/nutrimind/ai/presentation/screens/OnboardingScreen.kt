package com.nutrimind.ai.presentation.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nutrimind.ai.presentation.viewmodel.OnboardingViewModel
import com.nutrimind.ai.ui.theme.GradientEnd
import com.nutrimind.ai.ui.theme.GradientStart
import com.nutrimind.ai.ui.theme.PremiumGreen
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onFinished: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

    val pages = listOf(
        OnboardingPageData(
            title = "Eat Smart. Live Better",
            subtitle = "Track your meals effortlessly and stay in control of your nutrition every day.",
            icon = Icons.Default.Restaurant,
            quotes = listOf(
                "Small healthy choices lead to big results.",
                "Your body deserves the best fuel.",
                "Healthy habits start today."
            )
        ),
        OnboardingPageData(
            title = "Fuel Your Fitness Journey",
            subtitle = "Monitor calories, protein, and nutrients to achieve your body goals faster.",
            icon = Icons.Default.BarChart,
            quotes = listOf(
                "Progress is built one meal at a time.",
                "Strong body, strong mind.",
                "Consistency beats motivation."
            )
        ),
        OnboardingPageData(
            title = "AI Powered Nutrition Tracking",
            subtitle = "Get intelligent meal insights and personalized recommendations instantly.",
            icon = Icons.Default.AutoAwesome,
            quotes = listOf(
                "Eat with purpose.",
                "Track smarter, not harder.",
                "Wellness begins with awareness."
            )
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GradientStart, GradientEnd)
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Skip Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, end = 16.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                TextButton(onClick = {
                    viewModel.completeOnboarding()
                    onFinished()
                }) {
                    Text(
                        "Skip",
                        color = PremiumGreen,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { pageIndex ->
                OnboardingPage(pageData = pages[pageIndex])
            }

            // Bottom UI
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Page Indicator
                Row(
                    Modifier
                        .height(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(3) { iteration ->
                        val color = if (pagerState.currentPage == iteration) PremiumGreen else Color.LightGray
                        val width by animateDpAsState(
                            targetValue = if (pagerState.currentPage == iteration) 24.dp else 8.dp,
                            label = "width"
                        )
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(width = width, height = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Continue Button
                Button(
                    onClick = {
                        if (pagerState.currentPage < 2) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        } else {
                            viewModel.completeOnboarding()
                            onFinished()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PremiumGreen)
                ) {
                    Text(
                        text = if (pagerState.currentPage == 2) "Get Started" else "Continue",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun OnboardingPage(pageData: OnboardingPageData) {
    var quoteIndex by remember { mutableIntStateOf(0) }
    
    // Rotate quotes every 3 seconds for dynamic effect
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(3000)
            quoteIndex = (quoteIndex + 1) % pageData.quotes.size
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Illustration placeholder with animation
        Surface(
            modifier = Modifier
                .size(280.dp)
                .padding(bottom = 32.dp),
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.5f)
        ) {
            Icon(
                imageVector = pageData.icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(64.dp)
                    .fillMaxSize(),
                tint = PremiumGreen
            )
        }

        // Motivational Quote Bubble
        AnimatedContent(
            targetState = pageData.quotes[quoteIndex],
            transitionSpec = {
                fadeIn() + slideInVertically { it } togetherWith fadeOut() + slideOutVertically { -it }
            },
            label = "quote"
        ) { quote ->
            Surface(
                color = PremiumGreen.copy(alpha = 0.1f),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Text(
                    text = "“$quote”",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    color = PremiumGreen,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Text(
            text = pageData.title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
            color = Color.Black,
            lineHeight = 40.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = pageData.subtitle,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            lineHeight = 24.sp
        )
    }
}

data class OnboardingPageData(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val quotes: List<String>
)
