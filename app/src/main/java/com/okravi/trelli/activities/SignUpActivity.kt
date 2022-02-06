package com.okravi.trelli.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.okravi.trelli.R
import com.okravi.trelli.databinding.ActivitySignUpBinding

private var binding: ActivitySignUpBinding? = null

class SignUpActivity : BaseActivity() {
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

        binding?.btnSignUp?.setOnClickListener {
            registerUser()
        }

    }

    private fun validateForm(name: String,
                             email: String,
                             password: String): Boolean{
        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter a name")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter an email")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter a password")
                false
            }
            else -> {
                true
            }
        }
    }

    private fun registerUser(){
        val name: String = binding?.etName?.text.toString().trim {it <= ' '}
        val email: String = binding?.etEmail?.text.toString().trim {it <= ' '}
        val password: String = binding?.etPassword?.text.toString().trim {it <= ' '}
        
        if(validateForm(name, email, password)){
            Toast.makeText(this, "User can be registered", Toast.LENGTH_SHORT).show()
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}