package com.example.kotlinpracticemirea

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.kotlinpracticemirea.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val fragmentManager = supportFragmentManager


        fragmentManager.beginTransaction().replace(R.id.container,MainFragment()).commit()
        setContentView(binding.root)
    }
}