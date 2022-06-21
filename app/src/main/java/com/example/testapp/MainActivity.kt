package com.example.testapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.adapter.GenreAdapter
import com.example.testapp.adapter.NowPlayingAdapter
import com.example.testapp.adapter.TrendingAdapter
import com.example.testapp.architecture.*
import com.example.testapp.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.gson.Gson


private lateinit var binding: ActivityMainBinding

private lateinit var viewModel: MainActivityViewModel
lateinit var genreRepository: GenreRepository
lateinit var nowPlayingRepository: NowPlayingRepository
lateinit var trendingRepository: TrendingRepository
lateinit var searchMovieRepository: SearchMovieRepository

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        genreRepository = GenreRepository(apiService)
        nowPlayingRepository = NowPlayingRepository(apiService)
        trendingRepository = TrendingRepository(apiService)
        searchMovieRepository = SearchMovieRepository(apiService)

        viewModel = getViewModel()

        viewModel.getListGenre(getString(R.string.api_key))
        viewModel.getListNowPlaying(getString(R.string.api_key))
        viewModel.getTrending(getString(R.string.api_key))
        setupGenre()
        setupNowPlaying()
        setupTrending()

        setUI()

        getFCMToken()
    }

    private fun setUI() {
        binding.etSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                viewModel.getMovieByQuery(getString(R.string.api_key), v.text.toString())
                handled = true
                observeSearch()
            }
            handled
        })

        binding.ivLogout.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.server_client_id))
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this, OnCompleteListener<Void?> {
                val toSplash = Intent(this, SplashActivity::class.java)
                startActivity(toSplash)
            })
    }

    private fun observeSearch() {
        viewModel.listMovieByQuery?.observe(this, Observer { data ->
            val toMovieList = Intent(this, ListMovieActivity::class.java)

            val sharedPreferences: SharedPreferences =
                this.getSharedPreferences("PREF", Context.MODE_PRIVATE)

            val editor = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(data.results)
            editor.putString("dataMovieByQuery", json)
            editor.commit()

            startActivity(toMovieList)

            Log.d("kesini", data.results.toString())
        })
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
            binding.rvTrending.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            Log.d("kesini", data.trending.toString())
        })
    }

    private fun setupNowPlaying() {
        viewModel.listNowPlayingData?.observe(this, Observer { data ->
            val nowPlayingAdapter = NowPlayingAdapter(data.nowPlaying, this)
            binding.rvNowShowing.adapter = nowPlayingAdapter
            binding.rvNowShowing.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            Log.d("kesini", data.nowPlaying.toString())
        })
    }

    private fun setupGenre() {
        viewModel.listGenreData?.observe(this, Observer { data ->
            val genreAdapter = GenreAdapter(data.genres, this, false)
            binding.rvGenre.adapter = genreAdapter
            binding.rvGenre.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            binding.seeMoreGenre.setOnClickListener {
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
                return MainActivityViewModel(
                    genreRepository,
                    nowPlayingRepository,
                    trendingRepository,
                    searchMovieRepository
                ) as T
            }
        })[MainActivityViewModel::class.java]
    }
}