package com.example.testapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.testapp.adapter.GenreAdapter
import com.example.testapp.adapter.MoviesAdapter
import com.example.testapp.api.ApiClient
import com.example.testapp.api.ApiInterface
import com.example.testapp.architecture.ListMovieByGenreRepository
import com.example.testapp.architecture.ListMovieViewModel
import com.example.testapp.databinding.ActivityListMovieBinding
import com.example.testapp.localdb.AppLocalDatabase
import com.example.testapp.model.Genre
import com.example.testapp.model.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


private lateinit var binding: ActivityListMovieBinding

private lateinit var viewModel: ListMovieViewModel
lateinit var listMovieByGenreRepository: ListMovieByGenreRepository

class ListMovieActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        listMovieByGenreRepository = ListMovieByGenreRepository(apiService)
        viewModel = getViewModel()

        val db = Room.databaseBuilder(
            applicationContext,
            AppLocalDatabase::class.java, "movie_db"
        ).build()

        val userDao = db.movieDao()

        Thread {
            val users: List<Result> = userDao.getAllNowPlaying()
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

    private fun getViewModel(): ListMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ListMovieViewModel(listMovieByGenreRepository) as T
            }
        })[ListMovieViewModel::class.java]
    }

}
