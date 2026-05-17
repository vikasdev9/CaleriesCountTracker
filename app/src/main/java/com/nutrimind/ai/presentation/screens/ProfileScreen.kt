package com.nutrimind.ai.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nutrimind.ai.domain.model.ActivityLevel
import com.nutrimind.ai.domain.model.FitnessGoal
import com.nutrimind.ai.domain.model.UserProfile
import com.nutrimind.ai.presentation.components.NutriCard
import com.nutrimind.ai.presentation.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profile by viewModel.profile.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Health Profile", fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Personal Details", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            
            NutriCard {
                OutlinedTextField(
                    value = profile.name,
                    onValueChange = { viewModel.updateProfile(profile.copy(name = it)) },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedTextField(
                        value = profile.age.toString(),
                        onValueChange = { viewModel.updateProfile(profile.copy(age = it.toIntOrNull() ?: 0)) },
                        label = { Text("Age") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = profile.gender,
                        onValueChange = { viewModel.updateProfile(profile.copy(gender = it)) },
                        label = { Text("Gender") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedTextField(
                        value = if (profile.height == 0f) "" else profile.height.toString(),
                        onValueChange = { viewModel.updateProfile(profile.copy(height = it.toFloatOrNull() ?: 0f)) },
                        label = { Text("Height (cm)") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = if (profile.weight == 0f) "" else profile.weight.toString(),
                        onValueChange = { viewModel.updateProfile(profile.copy(weight = it.toFloatOrNull() ?: 0f)) },
                        label = { Text("Weight (kg)") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            Text("Fitness & Goals", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            
            NutriCard {
                Text("Fitness Goal", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FitnessGoal.entries.forEach { goal ->
                        FilterChip(
                            selected = profile.fitnessGoal == goal,
                            onClick = { viewModel.updateProfile(profile.copy(fitnessGoal = goal)) },
                            label = { Text(goal.description) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text("Activity Level", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                ActivityLevel.entries.forEach { level ->
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                        RadioButton(
                            selected = profile.activityLevel == level,
                            onClick = { viewModel.updateProfile(profile.copy(activityLevel = level)) }
                        )
                        Column {
                            Text(
                                level.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() },
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(level.description, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        }
                    }
                }
            }

            Text("Preferences", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            
            NutriCard {
                OutlinedTextField(
                    value = profile.dietPreference,
                    onValueChange = { viewModel.updateProfile(profile.copy(dietPreference = it)) },
                    label = { Text("Dietary Preference (e.g. Vegan)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Button(
                onClick = { viewModel.saveProfile() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Update Health Insights", fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = { /* Export Logic */ },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Export Health Report (PDF)")
            }

            TextButton(
                onClick = { /* Logout */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout", color = MaterialTheme.colorScheme.error)
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
