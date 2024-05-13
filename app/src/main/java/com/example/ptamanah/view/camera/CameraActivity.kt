package com.example.ptamanah.view.camera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.ptamanah.R
import com.example.ptamanah.data.repository.ScanRepo
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityBottomdialogFalseBinding
import com.example.ptamanah.databinding.ActivityBottomdialogTrueBinding
import com.example.ptamanah.databinding.ActivityCameraBinding
import com.example.ptamanah.view.myEvent.MyEventFragment.Companion.TOKEN
import com.example.ptamanah.viewModel.scan.ScanViewModel
import com.example.ptamanah.viewModel.scan.ScanViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.launch

class CameraActivity : AppCompatActivity() {
    private lateinit var barcodeScanner: BarcodeScanner
    private lateinit var bindingCamera: ActivityCameraBinding
    private lateinit var bindingTrue: ActivityBottomdialogTrueBinding
    private lateinit var bindingFalse: ActivityBottomdialogFalseBinding
    private lateinit var cameraController: LifecycleCameraController
    private var token : String? = ""
    private var id: String? = ""
    private val viewModel: ScanViewModel by viewModels {
        ScanViewModelFactory(ScanRepo(ApiConfig.getApiService()))
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showToast("Permission request granted")
            } else {
                showToast("Permission request denied")
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
            val bottomSheetDialog = BottomSheetDialog(this)
            val bottomSheetView = layoutInflater.inflate(R.layout.activity_bottomdialog_true, null)
            val tvIdBooking = bottomSheetView.findViewById<TextView>(R.id.tvIdBooking)
            val button = bottomSheetView.findViewById<Button>(R.id.button)

            val barcodeResults = result?.getValue(barcodeScanner)
            if (barcodeResults != null && barcodeResults.isNotEmpty() && barcodeResults.first() != null) {
                val barcode = barcodeResults[0]
                cameraController.unbind()

                Log.d("tokenCuy", token.toString())
                Log.d("idNyo", id.toString())

                lifecycleScope.launch {
                    viewModel.scanEvent( token.toString(), id.toString(),  barcode.rawValue.toString()).collect{ result ->
                        result.onSuccess { post->
                            if(post.error == false) {
                                tvIdBooking.text = barcode.rawValue
                                bottomSheetDialog.setContentView(bottomSheetView)
                                bindingTrue.tvTitleEvent.text = post.data?.event?.namaEvent
                                bindingTrue.tvDateEventStart.text = post.data?.event?.tanggalStart
                                bindingTrue.tvDateEventEnd.text = post.data?.event?.tanggalEnd
                                bindingTrue.tvTimeEventStart.text = post.data?.event?.waktuStart
                                bindingTrue.tvTimeEventEnd.text = post.data?.event?.waktuEnd
                                bindingTrue.tvLocationEvent.text = post.data?.event?.namaTempat
                                bindingTrue.tvTipeTiket.text = post.data?.eventTicket?.namaTiket

                                bottomSheetDialog.show()

                                Log.d("berhasilCuy", post.error.toString())
                            } else {
                                Log.d("gagalCuy", post.error.toString())
                            }
                        }
                        result.onFailure {
                            showToast("gagal")
                        }
                    }
                }

                button.setOnClickListener {
                    bottomSheetDialog.dismiss()
                    firstCall = true
                    startCamera()
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
        private const val TAG = "CameraActivity"
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
        const val CAMERAX_RESULT = 200
        const val ID_EVENT = "id_event"
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}