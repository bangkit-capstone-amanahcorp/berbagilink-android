package com.example.ptamanah.view.settings.banner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class NyobaFragment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(android.R.id.content, EditBanner()) // Memuat fragment ke dalam Activity
            transaction.commit()
        }
    }
}