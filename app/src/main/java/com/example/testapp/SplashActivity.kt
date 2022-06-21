package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.testapp.databinding.ActivitySplashBinding

private lateinit var binding: ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_trans, R.anim.fade_out_trans)
            finish()
        }, 3000) // 3000 is the delayed time in milliseconds.
    }
}