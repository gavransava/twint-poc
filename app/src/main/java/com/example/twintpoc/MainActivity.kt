package com.example.twintpoc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.twintpoc.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ActivityMainBinding.inflate(layoutInflater)
        setContentView(view.root)
    }
}