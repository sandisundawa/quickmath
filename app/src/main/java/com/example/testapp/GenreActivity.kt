package com.example.testapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testapp.adapter.GenreAdapter
import com.example.testapp.databinding.ActivityGenreBinding
import com.example.testapp.model.Genre
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
//        Hawk.init(this).build()
//        val dataGenre: List<Genre> = Hawk.get("dataGenre")

        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("PREF", Context.MODE_PRIVATE)

        val arrayItems: List<Genre>
        val serializedObject: String = sharedPreferences.getString("dataGenre", null).orEmpty()
        if (serializedObject != null) {
            val gson = Gson()
            val type = object : TypeToken<List<Genre?>>() {}.type
            arrayItems = gson.fromJson<List<Genre>>(serializedObject, type)
            val genreAdapter = GenreAdapter(arrayItems, this, true)
            binding.rvGenre.adapter = genreAdapter
            binding.rvGenre.layoutManager = GridLayoutManager(this, 3)
        }



    }
}