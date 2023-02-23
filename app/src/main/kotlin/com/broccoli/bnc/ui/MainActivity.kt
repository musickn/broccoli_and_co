package com.broccoli.bnc.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.broccoli.bnc.MobileNavigationDirections
import com.broccoli.bnc.R
import com.broccoli.bnc.databinding.ActivityMainBinding
import com.broccoli.bnc.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @Inject
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        getLocalEnrollStatus()
    }

    private fun getLocalEnrollStatus() {
        val didRequestAccess = preferences.getBoolean(Constants.DID_REQUEST_ACCESS, false)
        if (didRequestAccess) {
            findNavController(R.id.nav_host).navigate(
                MobileNavigationDirections.toEnrollCompleteFragment()
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
