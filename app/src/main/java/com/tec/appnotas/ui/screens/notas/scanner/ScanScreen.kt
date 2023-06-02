package com.tec.appnotas.ui.screens.notas.scanner

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.BackpressureStrategy
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.tec.appnotas.ui.global.GlobalProvider
import com.tec.appnotas.ui.navigator.main.ScaffoldScreen
import com.tec.appnotas.ui.navigator.main.Screens
import com.tec.appnotas.ui.screens.notas.UserVMState
import com.tec.appnotas.ui.screens.notas.UserViewmodel
import kotlinx.coroutines.launch


@Composable
fun ScanScreen(globalProvider: GlobalProvider, navHostController: NavHostController){
    val context = LocalContext.current
    val state = globalProvider.userVM.userState.collectAsState().value
    val cameraProviderFuture = remember{
        ProcessCameraProvider.getInstance(context)
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    var detected by remember { mutableStateOf(false)}
    var inserting by remember { mutableStateOf(false )}

    if(detected){
        Log.d("STATE",state.toString())
        if(state == UserVMState.CONNECTION_ERROR) {
            Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show()
            globalProvider.userVM.notifiedError()
        }
        navHostController.popBackStack(ScaffoldScreen.Home.route,false,false)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = { context ->
                val previewView = PreviewView(context)
                val preview = Preview.Builder().build()
                val selector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()
                preview.setSurfaceProvider(previewView.surfaceProvider)
                val imageAnalysis = ImageAnalysis.Builder()
                    .setTargetResolution(Size(previewView.width, previewView.height))
                    .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                imageAnalysis.setAnalyzer(
                    ContextCompat.getMainExecutor(context),
                    BarcodeAnalyzer { result ->
                        if(!inserting) {
                            inserting = true
                            globalProvider.userVM.getNotaFromCode(result.rawValue.toString())
                            detected = true
                        }
                    }
                )
                try {
                    cameraProviderFuture.get().bindToLifecycle(
                        lifecycleOwner,
                        selector,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                previewView
            },
            modifier = Modifier.weight(1f)
        )

        DisposableEffect(Unit) {
            onDispose {
                cameraProviderFuture.get().unbindAll()
            }
        }

    }

}