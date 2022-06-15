package com.example.testapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.adapter.MoviesAdapter
import com.example.testapp.api.ApiClient
import com.example.testapp.api.ApiInterface
import com.example.testapp.architecture.ListMovieByGenreRepository
import com.example.testapp.architecture.ListMovieViewModel
import com.example.testapp.databinding.ActivityListMovieBinding


private lateinit var binding: ActivityListMovieBinding

private lateinit var viewModel: ListMovieViewModel
lateinit var listMovieByGenreRepository: ListMovieByGenreRepository

class ListMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val apiService: ApiInterface = ApiClient.getClient()
        listMovieByGenreRepository = ListMovieByGenreRepository(apiService)
        viewModel = getViewModel()

        val intent = intent
        val genre = intent.getStringExtra("genreId")
        Log.d("kesini", "genre : "+genre)
        viewModel.getListMovieByGenre(getString(R.string.api_key),genre.orEmpty())

        setupMovies()

    }

    private fun setupMovies() {
        viewModel.listMovie?.observe(this, Observer { data ->
            Log.d("kesini", data.results.toString())
            val movieAdapter = MoviesAdapter(data.results.orEmpty(), this)
            binding.rvMovieByGenre.adapter = movieAdapter
            binding.rvMovieByGenre.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        })
    }

    private fun getViewModel(): ListMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ListMovieViewModel(listMovieByGenreRepository) as T
            }
        })[ListMovieViewModel::class.java]
    }

}
