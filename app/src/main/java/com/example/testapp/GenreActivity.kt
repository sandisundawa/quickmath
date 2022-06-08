package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.adapter.GenreAdapter
import com.example.testapp.databinding.ActivityGenreBinding
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.model.Genre
import com.orhanobut.hawk.Hawk

private lateinit var binding: ActivityGenreBinding

class GenreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenreBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupGenre()
    }

    private fun setupGenre() {
        Hawk.init(this).build()
        val dataGenre: List<Genre> = Hawk.get("dataGenre")

        val genreAdapter = GenreAdapter(dataGenre, this, true)
        binding.rvGenre.adapter = genreAdapter
        binding.rvGenre.layoutManager = GridLayoutManager(this, 3)

    }
}