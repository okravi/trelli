package com.okravi.trelli.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.okravi.trelli.R
import com.okravi.trelli.databinding.ActivityBaseBinding
import com.okravi.trelli.databinding.ActivityMainBinding
import com.okravi.trelli.databinding.NavHeaderMainBinding
import com.okravi.trelli.models.User

private var binding: ActivityMainBinding? = null
private var navViewHeaderBinding : NavHeaderMainBinding? = null

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val viewHeader = binding?.navView?.getHeaderView(0)
        navViewHeaderBinding = NavHeaderMainBinding.bind(viewHeader!!)

        setUpActionBar()
        binding?.navView?.setNavigationItemSelectedListener(this)

    }

    private fun setUpActionBar() {
        val toolbarMainActivity = binding?.includedAppBarMain?.toolbarMainActivity
        setSupportActionBar(toolbarMainActivity)
        toolbarMainActivity?.setNavigationIcon(R.drawable.ic_action_navigation_menu)

        toolbarMainActivity?.setNavigationOnClickListener {
            // Toggle Drawer
            toggleDrawer()
        }

    }

    private fun toggleDrawer() {
        if (binding?.drawerLayout!!.isDrawerOpen(GravityCompat.START)){
            binding?.drawerLayout?.closeDrawer(GravityCompat.START)
        }else{
            binding?.drawerLayout?.openDrawer(GravityCompat.START)
        }
    }

    fun updateNavigationUserDetails(user: User){
        Glide
            .with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.loading_spinner)
            .into(navViewHeaderBinding?.ivUserImage)

    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (binding?.drawerLayout!!.isDrawerOpen(GravityCompat.START)){
            binding?.drawerLayout?.closeDrawer(GravityCompat.START)
        }else{
            doubleBackToExit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId)
        {
            R.id.nav_my_profile -> {
                Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_sign_out -> {
                FirebaseAuth.getInstance().signOut()

                val intent = Intent(this, IntroActivity::class.java)
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                            or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
        binding?.drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }
}