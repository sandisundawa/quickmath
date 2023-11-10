package com.moviewers.testapp.testapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.moviewers.testapp.testapp.adapter.MoviesAdapter
import com.moviewers.testapp.testapp.architecture.ListMovieViewModel
import com.moviewers.testapp.testapp.databinding.ActivityListMovieBinding
import com.moviewers.testapp.testapp.model.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


private lateinit var binding: ActivityListMovieBinding

class ListMovieActivity : BaseActivity() {

    private lateinit var viewModel: ListMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel = ViewModelProvider(this).get(ListMovieViewModel::class.java)

        val movieDao = initLocalDB(this)
        Thread {
            val users: List<Result> = movieDao.getAllNowPlaying()
            Log.d("kesini", users.toString())
        }.start()

        val intent = intent
        val genre = intent.getStringExtra("genreId")

        if (genre != null) {
            viewModel.getDiscoverMovie(
                getString(R.string.api_key),
                genre.orEmpty(),
                sortBy = "popularity.desc"
            )
            setupMovies()
        } else getListMovieByQuery()

    }

    private fun getListMovieByQuery() {
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("PREF", Context.MODE_PRIVATE)

        val arrayItems: List<Result>
        val serializedObject: String =
            sharedPreferences.getString("dataMovieByQuery", null).orEmpty()
        val gson = Gson()
        val type = object : TypeToken<List<Result?>>() {}.type
        arrayItems = gson.fromJson(serializedObject, type)

        val adapter = MoviesAdapter(arrayItems, this)
        binding.rvMovieByGenre.adapter = adapter
        binding.rvMovieByGenre.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun setupMovies() {
        viewModel.listMovie?.observe(this, Observer { data ->
            val movieAdapter = MoviesAdapter(data.results.orEmpty(), this)
            binding.rvMovieByGenre.adapter = movieAdapter
            binding.rvMovieByGenre.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        })
    }
}
