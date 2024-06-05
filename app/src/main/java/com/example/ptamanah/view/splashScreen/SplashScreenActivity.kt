package com.example.ptamanah.view.splashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ptamanah.R
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.preference.dataStore
import com.example.ptamanah.data.repository.AuthRepo
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.view.eventTenant.DetailEventTenant
import com.example.ptamanah.view.main.HomePageAdmin
import com.example.ptamanah.view.main.HomePageCashier
import com.example.ptamanah.viewModel.event.EventTenantViewModel
import com.example.ptamanah.viewModel.factory.AuthViewModelFactory
import com.example.ptamanah.viewModel.main.MainViewModel
import com.example.ptamanah.viewModel.admin.mainadmin.HomePageAdminViewModel

class SplashScreenActivity : AppCompatActivity() {

    private val userPreference: UserPreference by lazy { UserPreference(this.dataStore) }
    private val viewModelCashier: MainViewModel by viewModels {
        AuthViewModelFactory(AuthRepo(ApiConfig.getApiService(), userPreference))
    }
    private val viewModelTenant: EventTenantViewModel by viewModels {
        AuthViewModelFactory(AuthRepo(ApiConfig.getApiService(), userPreference))
    }
    private val viewModelAdmin: HomePageAdminViewModel by viewModels {
        AuthViewModelFactory(AuthRepo(ApiConfig.getApiService(), userPreference))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            var sessionHandled = false

            viewModelTenant.getSessionTenant().observe(this) { tenantSession ->
                if (tenantSession != null) {
                    Log.d("tenantSes", tenantSession.toString())
                    startActivity(Intent(this, DetailEventTenant::class.java))
                    sessionHandled = true
                }
            }

            viewModelCashier.getSession().observe(this) { cashierSession ->
                if (cashierSession != null) {
                    Log.d("casSes", cashierSession.toString())
                    if (!sessionHandled) {
                        startActivity(Intent(this, HomePageCashier::class.java))
                    }
                    sessionHandled = true
                }
            }

            viewModelAdmin.getSession().observe(this) { adminSession ->
                if (adminSession != null) {
                    Log.d("adminSes", adminSession.toString())
                    if (!sessionHandled) {
                        startActivity(Intent(this, HomePageAdmin::class.java))
                    }
                    sessionHandled = true
                }
            }

            Handler(Looper.getMainLooper()).postDelayed({
                if (!sessionHandled) {
                    startActivity(Intent(this, HomePageCashier::class.java))
                }
                finish()
            }, 500)

        }, 2000)
    }
}
