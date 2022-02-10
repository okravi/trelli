package com.okravi.trelli.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.okravi.trelli.R
import com.okravi.trelli.databinding.ActivitySignUpBinding
import com.okravi.trelli.firebase.FirestoreClass
import com.okravi.trelli.models.User

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

    fun userRegisteredSuccess(){
        Toast.makeText(
            this@SignUpActivity,
            "You have successfully signed up",
            Toast.LENGTH_SHORT
        ).show()


        hideProgressDialog()
        FirebaseAuth.getInstance().signOut()
        finish()
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
            showProgressDialog(resources.getString(R.string.please_wait))

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    // If the registration is successfully done
                    if (task.isSuccessful) {

                        // Firebase registered user
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        // Registered Email
                        val registeredEmail = firebaseUser.email!!

                        val user = User(firebaseUser.uid, name, registeredEmail)
                        FirestoreClass().registerUser(this, user)
                    } else {
                        Toast.makeText(
                            this@SignUpActivity,
                            task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}