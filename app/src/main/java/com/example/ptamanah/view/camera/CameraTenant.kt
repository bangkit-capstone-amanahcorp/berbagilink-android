package com.example.ptamanah.view.camera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.ptamanah.R
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.preference.dataStore
import com.example.ptamanah.data.repository.ScanRepo
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityCameraTenantBinding
import com.example.ptamanah.view.myEvent.MyEventFragment
import com.example.ptamanah.viewModel.scan.ScanViewModel
import com.example.ptamanah.viewModel.scan.ScanViewModelFactory
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class CameraTenant : AppCompatActivity() {
    private lateinit var barcodeScanner: BarcodeScanner
    private lateinit var binding: ActivityCameraTenantBinding
    private lateinit var cameraController: LifecycleCameraController
    private var token: String? = ""
    private var id: String? = ""
    private val userPreference: UserPreference by lazy { UserPreference(this.dataStore) }
    private val viewModel: ScanViewModel by viewModels {
        ScanViewModelFactory(ScanRepo(ApiConfig.getApiService(), userPreference))
    }
    private var isCameraStarted = false

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
        binding = ActivityCameraTenantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }


        viewModel.getTokenTenant().observe(this) {
            token = it
            Log.d("TOKENAWAL", token.toString())
            if (!isCameraStarted) {
                startCamera()
            }

        }


        binding.backbtn.setOnClickListener {
            finish()
        }

    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()
    }

    private fun startCamera() {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        barcodeScanner = BarcodeScanning.getClient(options)

        id = intent.getStringExtra(ID_EVENT_TENANT)

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
        binding.viewFinder.controller = cameraController
        val aniSlide: Animation =
            AnimationUtils.loadAnimation(this@CameraTenant, R.anim.scanner_animation)
        binding.barcodeLine.startAnimation(aniSlide)

        isCameraStarted = true
    }


    private var firstCall = true
    private fun showResult(result: MlKitAnalyzer.Result?) {
        binding.barcodeLine.visibility = View.VISIBLE

        if (firstCall) {
            val barcodeResults = result?.getValue(barcodeScanner)
            val slideUpAnimation = AnimationUtils.loadAnimation(this@CameraTenant, R.anim.slide_up)

            if (barcodeResults != null && barcodeResults.isNotEmpty() && barcodeResults.first() != null) {
                val barcode = barcodeResults[0]

                val boxLeft = binding.camerashape.left
                val boxRight = binding.camerashape.right
                val boxTop = binding.camerashape.top
                val boxBottom = binding.camerashape.bottom

                val barcodeBox = barcode.boundingBox ?: return

                val barcodeInsideBox = (barcodeBox.left >= boxLeft && barcodeBox.right <= boxRight
                        && barcodeBox.top >= boxTop && barcodeBox.bottom <= boxBottom)

                if (barcodeInsideBox) {
                    binding.barcodeLine.clearAnimation()
                    binding.barcodeLine.visibility = View.GONE
                    cameraController.unbind()
                    lifecycleScope.launch {
                        viewModel.scanTenant(
                            token.toString(),
                            id.toString(),
                            barcode.rawValue.toString()
                        ).collect { result ->
                            result.onSuccess { post ->
                                binding.cardViewResultScan.visibility = View.VISIBLE
                                binding.cardViewFailResult.visibility = View.GONE
                                binding.cardViewResultScan.startAnimation(slideUpAnimation)
                                binding.apply {
                                    tvIdTiketIsi.text = post.data?.ticketId.toString()
                                    tvNamaVisitorIsi.text = post.data?.nama
                                    tvEmailIsi.text = post.data?.email
                                    tvNoHpIsi.text = post.data?.noTelp
                                    tvTglIsi.text = post.data?.visitedAt
                                }
                                binding.button.setOnClickListener {
                                    binding.cardViewResultScan.clearAnimation()
                                    binding.cardViewResultScan.visibility = View.GONE
                                    startCamera()
                                }
                            }
                            result.onFailure { exception ->
                                val errorMessage = if (exception is HttpException) {
                                    try {
                                        val errorBody = exception.response()?.errorBody()?.string().toString()
                                        JSONObject(errorBody).getString("message")
                                    } catch (e: Exception) {
                                        "Terjadi kesalahan"
                                    }
                                } else {
                                    "Terjadi kesalahan"
                                }
                                
                                binding.cardViewFailResult.visibility = View.VISIBLE
                                binding.cardViewFailResult.startAnimation(slideUpAnimation)
                                binding.tvdeskripsifailed.text = errorMessage
                                binding.btnRescan.setOnClickListener {
                                    binding.cardViewFailResult.clearAnimation()
                                    binding.cardViewFailResult.visibility = View.GONE
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
        const val ID_EVENT_TENANT = "idEvent"
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}