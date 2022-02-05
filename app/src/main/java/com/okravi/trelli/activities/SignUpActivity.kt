package com.okravi.trelli.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.okravi.trelli.R
import com.okravi.trelli.databinding.ActivitySignUpBinding

private var binding: ActivitySignUpBinding? = null

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setupActionBar()


    }

    private fun setupActionBar(){
        setSupportActionBar(binding?.toolbarSignUpActivity)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)

        binding?.toolbarSignUpActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}