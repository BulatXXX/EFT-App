package com.singularity.eft_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI

import com.singularity.eft_app.databinding.ActivityMainBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
        binding.toolbar.title = "EFT-APP"

        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        NavigationUI.setupWithNavController(
            navigationBarView = binding.bottomNavigationView,
            navController = Navigation.findNavController(this, binding.navHostFragment.id)
        )
    }


}