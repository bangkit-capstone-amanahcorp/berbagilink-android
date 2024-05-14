package com.example.ptamanah.view.camera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.ptamanah.data.repository.ScanRepo
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityCameraBinding
import com.example.ptamanah.view.myEvent.MyEventFragment.Companion.TOKEN
import com.example.ptamanah.viewModel.scan.ScanViewModel
import com.example.ptamanah.viewModel.scan.ScanViewModelFactory
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.launch

class CameraActivity : AppCompatActivity() {
    private lateinit var barcodeScanner: BarcodeScanner
    private lateinit var bindingCamera: ActivityCameraBinding
    private lateinit var cameraController: LifecycleCameraController
    private var token: String? = ""
    private var id: String? = ""
    private val viewModel: ScanViewModel by viewModels {
        ScanViewModelFactory(ScanRepo(ApiConfig.getApiService()))
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showToast("Izin diberikan")
            } else {
                showToast("Beri izin untuk menggunakan kamera")
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingCamera = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(bindingCamera.root)
        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }
        startCamera()
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
    }

    private fun startCamera() {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        barcodeScanner = BarcodeScanning.getClient(options)

        id = intent.getStringExtra(ID_EVENT)
        token = intent.getStringExtra(TOKEN)

        val analyzer = MlKitAnalyzer(
            listOf(barcodeScanner),
            CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED,
            ContextCompat.getMainExecutor(this)
        ) { result: MlKitAnalyzer.Result? ->
            showResult(result)
        }
        cameraController = LifecycleCameraController(baseContext)
        cameraController.setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(this),
            analyzer
        )
        cameraController.bindToLifecycle(this)
        bindingCamera.viewFinder.controller = cameraController
    }

    private var firstCall = true
    private fun showResult(result: MlKitAnalyzer.Result?) {
        if (firstCall) {
            val barcodeResults = result?.getValue(barcodeScanner)
            if (barcodeResults != null && barcodeResults.isNotEmpty() && barcodeResults.first() != null) {
                val barcode = barcodeResults[0]
                cameraController.unbind()
                lifecycleScope.launch {
                    viewModel.scanEvent(
                        token.toString(),
                        id.toString(),
                        barcode.rawValue.toString()
                    ).collect { result ->
                        result.onSuccess { post ->
                            bindingCamera.cardViewResultScan.visibility = View.VISIBLE
                            bindingCamera.cardViewFailResult.visibility = View.GONE

                            bindingCamera.tvIdBooking.text = barcode.rawValue
                            bindingCamera.tvTitleEvent.text = post.data?.event?.namaEvent
                            bindingCamera.tvDateEventStart.text = post.data?.event?.tanggalStart
                            bindingCamera.tvDateEventEnd.text = post.data?.event?.tanggalEnd
                            bindingCamera.tvTimeEventStart.text = post.data?.event?.waktuStart
                            bindingCamera.tvTimeEventEnd.text = post.data?.event?.waktuEnd
                            bindingCamera.tvLocationEvent.text = post.data?.event?.namaTempat
                            bindingCamera.tvTipeTiket.text = post.data?.eventTicket?.namaTiket

                            bindingCamera.button.setOnClickListener {
                                bindingCamera.cardViewResultScan.visibility = View.GONE
                                startCamera()
                            }
                        }
                        result.onFailure {
                            bindingCamera.cardViewFailResult.visibility = View.VISIBLE
                            bindingCamera.btnRescan.setOnClickListener {
                                bindingCamera.cardViewFailResult.visibility = View.GONE
                                startCamera()
                            }
                        }
                    }
                    firstCall = true
                }
                firstCall = false
            }
        }
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val ID_EVENT = "id_event"
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}