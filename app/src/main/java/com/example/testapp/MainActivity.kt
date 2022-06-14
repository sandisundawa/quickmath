package com.example.testapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.adapter.GenreAdapter
import com.example.testapp.adapter.NowPlayingAdapter
import com.example.testapp.adapter.TrendingAdapter
import com.example.testapp.api.ApiClient
import com.example.testapp.api.ApiInterface
import com.example.testapp.architecture.GenreRepository
import com.example.testapp.architecture.MainActivityViewModel
import com.example.testapp.architecture.NowPlayingRepository
import com.example.testapp.architecture.TrendingRepository
import com.example.testapp.databinding.ActivityMainBinding
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.gson.Gson


private lateinit var binding: ActivityMainBinding

private lateinit var viewModel: MainActivityViewModel
lateinit var genreRepository: GenreRepository
lateinit var nowPlayingRepository: NowPlayingRepository
lateinit var trendingRepository: TrendingRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val apiService: ApiInterface = ApiClient.getClient()
        genreRepository = GenreRepository(apiService)
        nowPlayingRepository = NowPlayingRepository(apiService)
        trendingRepository = TrendingRepository(apiService)
        viewModel = getViewModel()

        viewModel.getListGenre(getString(R.string.api_key))
        viewModel.getListNowPlaying(getString(R.string.api_key))
        viewModel.getTrending(getString(R.string.api_key))
        setupGenre()
        setupNowPlaying()
        setupTrending()
        getFCMToken()

    }

    private fun getFCMToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(
            this
        ) { instanceIdResult: InstanceIdResult ->
            val newToken = instanceIdResult.token
            Log.e("newToken", newToken)
            this.getPreferences(MODE_PRIVATE).edit()
                .putString("token", newToken).apply()
        }
    }

    private fun setupTrending() {
        viewModel.listTrendingData?.observe(this, Observer { data ->
            val trendingAdapter = TrendingAdapter(data.trending, this)
            binding.rvTrending.adapter = trendingAdapter
            binding.rvTrending.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            Log.d("kesini", data.trending.toString())
        })
    }

    private fun setupNowPlaying() {
        viewModel.listNowPlayingData?.observe(this, Observer { data ->
            val nowPlayingAdapter = NowPlayingAdapter(data.nowPlaying, this)
            binding.rvNowShowing.adapter = nowPlayingAdapter
            binding.rvNowShowing.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            Log.d("kesini", data.nowPlaying.toString())
        })
    }

    private fun setupGenre() {
        viewModel.listGenreData?.observe(this, Observer { data ->
            val genreAdapter = GenreAdapter(data.genres, this, false)
            binding.rvGenre.adapter = genreAdapter
            binding.rvGenre.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)

            binding.seeMoreGenre.setOnClickListener {
//                Hawk.init(this).build()
//                Hawk.put("dataGenre", data.genres)
                val toGenre = Intent(this, GenreActivity::class.java)

                val sharedPreferences: SharedPreferences =
                    this.getSharedPreferences("PREF", Context.MODE_PRIVATE)

                val editor = sharedPreferences.edit()
                val gson = Gson()
                val json = gson.toJson(data.genres)
                editor.putString("dataGenre", json)
                editor.commit()

                startActivity(toGenre)
            }
        })
    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(genreRepository, nowPlayingRepository, trendingRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }
}