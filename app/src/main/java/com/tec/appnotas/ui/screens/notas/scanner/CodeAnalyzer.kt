package com.tec.appnotas.ui.screens.notas.scanner

import android.annotation.SuppressLint
import android.graphics.ImageFormat
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.nio.ByteBuffer

class BarcodeAnalyzer(private val onBarcodeDetected: (Barcode) -> Unit) : ImageAnalysis.Analyzer {
    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .build()

    private val barcodeScanner = BarcodeScanning.getClient(options)

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        Log.d("ANALYZER","FUNCTION")
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            barcodeScanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    Log.d("BARCODES",barcodes.toString())
                    Log.d("BARCODES",barcodes.size.toString())
                    for (barcode in barcodes) {
                        Log.d("BARCODE",barcode.rawValue.toString())
                        onBarcodeDetected(barcode)
                    }
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }
}


//class CodeAnalyzer(
//    private val onQrCodeScanned: (String) -> Unit
//) : ImageAnalysis.Analyzer{
//
//    private val supportedFormats = listOf(
//        ImageFormat.YUV_420_888,
//        ImageFormat.YUV_422_888,
//        ImageFormat.YUV_444_888,
//        ImageFormat.JPEG,
//        ImageFormat.RGB_565
//    )
//
//    override fun analyze(image: ImageProxy) {
//        if(image.format in supportedFormats){
//            val bytes = image.planes.first().buffer.toByteArray()
//
//
//            val source = PlanarYUVLuminanceSource(bytes,image.width,image.height,0,0,image.width,image.height,false)
//            val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
//
//            Log.d("Analyzer","BINARY BITMAP SETUP")
//            try{
//                val result = MultiFormatReader().apply {
//                    setHints(mapOf(
//                        DecodeHintType.POSSIBLE_FORMATS to arrayListOf(BarcodeFormat.QR_CODE),
//                        DecodeHintType.TRY_HARDER to true
//                    ))
//                }.decodeWithState(binaryBitmap)
//                onQrCodeScanned(result.text)
//            } catch (e: Exception){
//                Log.d("Analyzer",e.stackTraceToString())
//            } finally{
//                image.close()
//            }
//        }
//        else{
//            Log.d("CODEANALYZER","UNSUPPORTED IMAGE FORMAT: ${image.format}")
//        }
//    }
//
//    private fun ByteBuffer.toByteArray(): ByteArray{
//        return ByteArray(remaining()).also{
//            get(it)
//        }
//    }
//}