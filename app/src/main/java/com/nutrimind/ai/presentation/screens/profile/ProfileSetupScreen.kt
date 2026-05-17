package com.nutrimind.ai.presentation.screens.profile

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nutrimind.ai.domain.model.ActivityLevel
import com.nutrimind.ai.domain.model.FitnessGoal
import com.nutrimind.ai.presentation.viewmodel.ProfileViewModel

@Composable
fun ProfileSetupScreen(
    onFinished: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    var step by remember { mutableStateOf(1) }
    val profile by viewModel.profile.collectAsState()

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (step > 1) {
                    TextButton(onClick = { step-- }) {
                        Text("Back")
                    }
                } else {
                    Spacer(modifier = Modifier.width(1.dp))
                }

                Button(
                    onClick = {
                        if (step < 4) {
                            step++
                        } else {
                            viewModel.saveProfile()
                            onFinished()
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text(if (step == 4) "Finish" else "Next")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                progress = { step / 4f },
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
            )

            AnimatedContent(
                targetState = step,
                transitionSpec = {
                    slideInHorizontally { it } + fadeIn() togetherWith
                            slideOutHorizontally { -it } + fadeOut()
                }, label = ""
            ) { targetStep ->
                when (targetStep) {
                    1 -> StepBasicInfo(profile, viewModel)
                    2 -> StepPhysicalInfo(profile, viewModel)
                    3 -> StepGoals(profile, viewModel)
                    4 -> StepPreferences(profile, viewModel)
                }
            }
        }
    }
}

@Composable
fun StepBasicInfo(profile: com.nutrimind.ai.domain.model.UserProfile, viewModel: ProfileViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Tell us about yourself", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = profile.name,
            onValueChange = { viewModel.updateProfile(profile.copy(name = it)) },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Add Gender selection etc.
    }
}

@Composable
fun StepPhysicalInfo(profile: com.nutrimind.ai.domain.model.UserProfile, viewModel: ProfileViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Physical Details", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = if(profile.height == 0f) "" else profile.height.toString(),
            onValueChange = { viewModel.updateProfile(profile.copy(height = it.toFloatOrNull() ?: 0f)) },
            label = { Text("Height (cm)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = if(profile.weight == 0f) "" else profile.weight.toString(),
            onValueChange = { viewModel.updateProfile(profile.copy(weight = it.toFloatOrNull() ?: 0f)) },
            label = { Text("Weight (kg)") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun StepGoals(profile: com.nutrimind.ai.domain.model.UserProfile, viewModel: ProfileViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("What's your goal?", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        FitnessGoal.entries.forEach { goal ->
            FilterChip(
                selected = profile.fitnessGoal == goal,
                onClick = { viewModel.updateProfile(profile.copy(fitnessGoal = goal)) },
                label = { Text(goal.description) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
fun StepPreferences(profile: com.nutrimind.ai.domain.model.UserProfile, viewModel: ProfileViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Activity Level", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        ActivityLevel.entries.forEach { level ->
            FilterChip(
                selected = profile.activityLevel == level,
                onClick = { viewModel.updateProfile(profile.copy(activityLevel = level)) },
                label = { Text(level.description) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )
        }
    }
}
