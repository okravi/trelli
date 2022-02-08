package com.okravi.trelli.activities

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.okravi.trelli.R
import com.okravi.trelli.databinding.ActivitySigninBinding

private var binding: ActivitySigninBinding? = null

class SigninActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        auth = FirebaseAuth.getInstance()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding?.btnSignIn?.setOnClickListener {
            signInRegisteredUser()
        }
        setupActionBar()
    }

    private fun setupActionBar(){
        setSupportActionBar(binding?.toolbarSignInActivity)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)

        binding?.toolbarSignInActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun signInRegisteredUser(){
        val email: String = binding?.etEmail?.text.toString().trim {it <= ' '}
        val password: String = binding?.etPassword?.text.toString().trim {it <= ' '}

        if (validateForm(email, password)){
            showProgressDialog(resources.getString(R.string.please_wait))

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Sign in", "signInWithEmail:success")
                        val user = auth.currentUser
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Sign in", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }
        }
    }

    private fun validateForm(email: String,
                             password: String): Boolean{
        return when {

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
}