package com.okravi.trelli.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import com.okravi.trelli.R
import com.okravi.trelli.databinding.ActivityMyProfileBinding

private var binding: ActivityMyProfileBinding? = null

class MyProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpActionBar()
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


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}