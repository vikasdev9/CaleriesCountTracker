package com.nutrimind.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.nutrimind.ai.presentation.navigation.NavGraph
import com.nutrimind.ai.ui.theme.NutriMindAITheme
import dagger.hilt.android.AndroidEntryPoint

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.nutrimind.ai.presentation.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutriMindAITheme {
                val viewModel: MainViewModel = hiltViewModel()
                val startDestination by viewModel.startDestination.collectAsState()
                
                Surface(color = MaterialTheme.colorScheme.background) {
                    if (startDestination != null) {
                        val navController = rememberNavController()
                        NavGraph(navController = navController, startDestination = startDestination!!)
                    }
                }
            }
        }
    }
}
