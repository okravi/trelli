package com.okravi.trelli

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.okravi.trelli.databinding.ActivitySplashBinding

var binding: ActivitySplashBinding? = null

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val typeFace : Typeface = Typeface.createFromAsset(assets,"font/carbon bl.ttf")
        binding?.tvAppName?.typeface = typeFace
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}