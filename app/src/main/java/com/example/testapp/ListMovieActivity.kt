package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testapp.databinding.ActivityListMovieBinding
import com.example.testapp.databinding.ActivityMainBinding

private lateinit var binding: ActivityListMovieBinding

class ListMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val genre = intent.getStringExtra("genreName")
    }
}