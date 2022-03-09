package com.phoenixdevelopers.pixabay.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.phoenixdevelopers.pixabay.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding
            .inflate(layoutInflater)

        setContentView(_binding.root)

    }
}