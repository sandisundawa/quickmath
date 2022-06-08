package com.example.testapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.adapter.GenreAdapter
import com.example.testapp.api.ApiClient
import com.example.testapp.api.ApiInterface
import com.example.testapp.architecture.GenreRepository
import com.example.testapp.architecture.MainActivityViewModel
import com.example.testapp.databinding.ActivityMainBinding
import com.orhanobut.hawk.Hawk

private lateinit var binding: ActivityMainBinding

private lateinit var viewModel: MainActivityViewModel
lateinit var genreRepository: GenreRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val apiService: ApiInterface = ApiClient.getClient()
        genreRepository = GenreRepository(apiService)
        viewModel = getViewModel()

        viewModel.getListGenre()
        setupGenre()


    }

    private fun setupGenre() {
        viewModel.listGenreData?.observe(this, Observer { data ->
            val genreAdapter = GenreAdapter(data.genres, this, false)
            binding.rvGenre.adapter = genreAdapter
            binding.rvGenre.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)

            binding.seeMoreGenre.setOnClickListener {
                Hawk.init(this).build()
                Hawk.put("dataGenre", data.genres)
                val toGenre = Intent(this, GenreActivity::class.java)
                startActivity(toGenre)
            }
        })
    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(genreRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }
}