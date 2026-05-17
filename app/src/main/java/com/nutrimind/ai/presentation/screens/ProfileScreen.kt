package com.nutrimind.ai.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nutrimind.ai.domain.model.UserProfile
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
                title = { Text("Your Profile", fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = profile.name,
                onValueChange = { viewModel.updateProfile(profile.copy(name = it)) },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = profile.age.toString(),
                    onValueChange = { viewModel.updateProfile(profile.copy(age = it.toIntOrNull() ?: 0)) },
                    label = { Text("Age") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = profile.gender,
                    onValueChange = { viewModel.updateProfile(profile.copy(gender = it)) },
                    label = { Text("Gender") },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = profile.height.toString(),
                    onValueChange = { viewModel.updateProfile(profile.copy(height = it.toFloatOrNull() ?: 0f)) },
                    label = { Text("Height (cm)") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = profile.weight.toString(),
                    onValueChange = { viewModel.updateProfile(profile.copy(weight = it.toFloatOrNull() ?: 0f)) },
                    label = { Text("Weight (kg)") },
                    modifier = Modifier.weight(1f)
                )
            }

            Button(
                onClick = { viewModel.saveProfile() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Save Profile")
            }

            OutlinedButton(
                onClick = { /* Export PDF Logic */ },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Export PDF Report")
            }

            TextButton(
                onClick = { /* Logout Logic */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
