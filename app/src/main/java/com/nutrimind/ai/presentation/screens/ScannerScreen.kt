package com.nutrimind.ai.presentation.screens

import android.Manifest
import android.content.pm.PackageManager
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.nutrimind.ai.presentation.screens.scanner.ScanResultScreen
import com.nutrimind.ai.presentation.viewmodel.ScannerUiState
import com.nutrimind.ai.presentation.viewmodel.ScannerViewModel
import com.nutrimind.ai.utils.BarcodeAnalyzer
import com.nutrimind.ai.utils.TextAnalyzer
import java.util.concurrent.Executors

enum class ScanMode { BARCODE, OCR }

@Composable
fun ScannerScreen(
    onNavigateBack: () -> Unit,
    viewModel: ScannerViewModel = hiltViewModel()
) {
    var scanMode by remember { mutableStateOf(ScanMode.BARCODE) }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    val uiState by viewModel.uiState.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (!hasCameraPermission) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Camera permission is required to scan foods",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) }) {
                    Text("Grant Permission")
                }
            }
        } else {
            when (val state = uiState) {
                is ScannerUiState.Idle, is ScannerUiState.Loading -> {
                    key(scanMode) {
                        AndroidView(
                            factory = { context ->
                                val previewView = PreviewView(context).apply {
                                    scaleType = PreviewView.ScaleType.FILL_CENTER
                                }
                                val cameraProviderFuture = androidx.camera.lifecycle.ProcessCameraProvider.getInstance(context)

                                cameraProviderFuture.addListener({
                                    val cameraProvider = cameraProviderFuture.get()
                                    val preview = Preview.Builder().build().also {
                                        it.setSurfaceProvider(previewView.surfaceProvider)
                                    }

                                    val imageAnalysis = ImageAnalysis.Builder()
                                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                        .build()
                                        .also {
                                            it.setAnalyzer(cameraExecutor, if (scanMode == ScanMode.BARCODE) {
                                                BarcodeAnalyzer { barcode ->
                                                    viewModel.onBarcodeScanned(barcode)
                                                }
                                            } else {
                                                TextAnalyzer { text ->
                                                    if (text.length > 50) {
                                                        viewModel.onIngredientsScanned(text)
                                                    }
                                                }
                                            })
                                        }

                                    try {
                                        cameraProvider.unbindAll()
                                        cameraProvider.bindToLifecycle(
                                            lifecycleOwner,
                                            CameraSelector.DEFAULT_BACK_CAMERA,
                                            preview,
                                            imageAnalysis
                                        )
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }, ContextCompat.getMainExecutor(context))
                                previewView
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    if (state is ScannerUiState.Loading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    // UI overlay
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row {
                            FilterChip(
                                selected = scanMode == ScanMode.BARCODE,
                                onClick = { scanMode = ScanMode.BARCODE },
                                label = { Text("Barcode") },
                                colors = FilterChipDefaults.filterChipColors(labelColor = Color.White)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            FilterChip(
                                selected = scanMode == ScanMode.OCR,
                                onClick = { scanMode = ScanMode.OCR },
                                label = { Text("Label/OCR") },
                                colors = FilterChipDefaults.filterChipColors(labelColor = Color.White)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            if (scanMode == ScanMode.BARCODE) "Align barcode inside the frame" else "Point at ingredients list",
                            color = Color.White
                        )
                    }
                }
                is ScannerUiState.Result -> {
                    ScanResultScreen(
                        entry = state.entry,
                        analysis = state.analysis,
                        onClose = { viewModel.reset() }
                    )
                }
                is ScannerUiState.OcrResult -> {
                    ScanResultScreen(
                        entry = null,
                        analysis = state.analysis,
                        onClose = { viewModel.reset() }
                    )
                }
                is ScannerUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(state.message, color = MaterialTheme.colorScheme.error)
                            Button(onClick = { viewModel.reset() }) {
                                Text("Try Again")
                            }
                        }
                    }
                }
            }
        }
    }
}
