package com.nutrimind.ai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrimind.ai.domain.model.FoodEntry
import com.nutrimind.ai.domain.model.NutritionAnalysis
import com.nutrimind.ai.domain.usecase.AnalyzeProductUseCase
import com.nutrimind.ai.domain.usecase.SearchFoodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val searchFoodUseCase: SearchFoodUseCase,
    private val analyzeProductUseCase: AnalyzeProductUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScannerUiState>(ScannerUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun onBarcodeScanned(barcode: String) {
        viewModelScope.launch {
            _uiState.value = ScannerUiState.Loading
            val foodEntry = searchFoodUseCase.byBarcode(barcode)
            if (foodEntry != null) {
                val analysis = analyzeProductUseCase.fromText(foodEntry.name)
                _uiState.value = ScannerUiState.Result(foodEntry, analysis)
            } else {
                _uiState.value = ScannerUiState.Error("Product not found")
            }
        }
    }

    fun onIngredientsScanned(ocrText: String) {
        viewModelScope.launch {
            _uiState.value = ScannerUiState.Loading
            val analysis = analyzeProductUseCase.fromIngredients(ocrText)
            _uiState.value = ScannerUiState.OcrResult(analysis)
        }
    }

    fun reset() {
        _uiState.value = ScannerUiState.Idle
    }
}

sealed class ScannerUiState {
    object Idle : ScannerUiState()
    object Loading : ScannerUiState()
    data class Result(val entry: FoodEntry, val analysis: NutritionAnalysis) : ScannerUiState()
    data class OcrResult(val analysis: NutritionAnalysis) : ScannerUiState()
    data class Error(val message: String) : ScannerUiState()
}
