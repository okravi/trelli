package com.okravi.trelli.activities

import android.os.Bundle
import com.bumptech.glide.Glide
import com.okravi.trelli.R
import com.okravi.trelli.databinding.ActivityMyProfileBinding
import com.okravi.trelli.firebase.FirestoreClass
import com.okravi.trelli.models.User

private var binding: ActivityMyProfileBinding? = null

class MyProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpActionBar()

        FirestoreClass().loadUserData(this)
    }

    private fun setUpActionBar() {
        val toolbarMainActivity = binding?.toolbarMyProfileActivity
        setSupportActionBar(toolbarMainActivity)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = resources.getString(R.string.my_profile)
        }

        toolbarMainActivity?.setNavigationOnClickListener { onBackPressed() }
    }

    fun setUserDataInUI(user: User){
        Glide
            .with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(binding?.ivUserImage!!)

        binding?.etName?.setText(user.name)
        binding?.etEmail?.setText(user.email)

        if (user.mobile != 0L){
            binding?.etMobile?.setText(user.mobile.toString())
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}