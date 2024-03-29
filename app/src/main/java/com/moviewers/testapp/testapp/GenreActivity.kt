package com.moviewers.testapp.testapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.moviewers.testapp.testapp.adapter.GenreAdapter
import com.moviewers.testapp.testapp.databinding.ActivityGenreBinding
import com.moviewers.testapp.testapp.model.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("PREF", Context.MODE_PRIVATE)

        val arrayItems: List<Genre>
        val serializedObject: String = sharedPreferences.getString("dataGenre", null).orEmpty()
        val gson = Gson()
        val type = object : TypeToken<List<Genre?>>() {}.type
        arrayItems = gson.fromJson(serializedObject, type)

        val genreAdapter = GenreAdapter(arrayItems, this, true)
        binding.rvGenre.adapter = genreAdapter
        binding.rvGenre.layoutManager = GridLayoutManager(this, 3)
    }
}