package com.example.testapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.testapp.adapter.GenreAdapter
import com.example.testapp.adapter.NowPlayingAdapter
import com.example.testapp.adapter.TrendingAdapter
import com.example.testapp.architecture.*
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.di.*
import com.example.testapp.localdb.AppLocalDatabase
import com.example.testapp.model.Genre
import com.example.testapp.model.Result
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.gson.Gson
import com.orhanobut.hawk.Hawk
import javax.inject.Inject
import kotlin.system.exitProcess


private lateinit var binding: ActivityMainBinding

private lateinit var viewModel: MainActivityViewModel
lateinit var genreRepository: GenreRepository
lateinit var nowPlayingRepository: NowPlayingRepository
lateinit var trendingRepository: TrendingRepository
lateinit var searchMovieRepository: SearchMovieRepository
lateinit var sessionRepository: SessionRepository

class MainActivity : BaseActivity() {

    @Inject lateinit var apiKey: ApiKey
    private var mesinComponent: MesinComponent? = null
    private var listData : List<Result>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Testing dagger2
        DaggerComponent.create().injectOnMain(this)
        Log.d("kesini 1", apiKey.getMesin())

        // with @Scope
        val house = House()
        mesinComponent = DaggerMesinComponent.create()
        mesinComponent!!.inject(house)
        Log.d("kesini 2", house.uniqueMesin.jumlah.toString())

        genreRepository = GenreRepository(apiService)
        nowPlayingRepository = NowPlayingRepository(apiService)
        trendingRepository = TrendingRepository(apiService)
        searchMovieRepository = SearchMovieRepository(apiService)
        sessionRepository = SessionRepository(apiService)

        viewModel = getViewModel()



        viewModel.getListGenre(apiKey.getApiKey())
        viewModel.getListNowPlaying(apiKey.getApiKey())
        viewModel.getTrending(apiKey.getApiKey())
        viewModel.getSession(apiKey.getApiKey())

        observeGenre()
        observeNowPlaying()
        observeTrending()
        observeSession()

        setUI()
        getFCMToken()
    }

    private fun observeSession() {
        viewModel.sessionData?.observe(this, Observer { data ->
            Hawk.init(this).build()
            Hawk.put("sessionId", data.guest_session_id)
        })
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
            showConfirmDialog("Logout", "Are you sure want to Logout?",
                "Yes", "Cancel", onPositifClicked = { signOut() })
        }

        binding.ivFilter.setOnClickListener {

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

    private fun observeTrending() {
        viewModel.listTrendingData?.observe(this, Observer { data ->
            val trendingAdapter = TrendingAdapter(data.trending, this)
            binding.loadingPanel1.visibility = View.GONE
            binding.rvTrending.adapter = trendingAdapter
            binding.rvTrending.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            Log.d("kesini", data.trending.toString())

            binding.seeMoreTrending.setOnClickListener {
                val toMovieList = Intent(this, ListMovieActivity::class.java)

                val sharedPreferences: SharedPreferences =
                    this.getSharedPreferences("PREF", Context.MODE_PRIVATE)

                val editor = sharedPreferences.edit()
                val gson = Gson()
                val json = gson.toJson(data.trending)
                editor.putString("dataMovieByQuery", json)
                editor.commit()

                startActivity(toMovieList)
            }
        })
    }

    private fun observeNowPlaying() {
        viewModel.listNowPlayingData?.observe(this, Observer { data ->
            val nowPlayingAdapter = NowPlayingAdapter(data.nowPlaying, this)
            binding.loadingPanel2.visibility = View.GONE
            binding.rvNowShowing.adapter = nowPlayingAdapter
            binding.rvNowShowing.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            Log.d("kesini", data.nowPlaying.toString())

            val movieDao = initLocalDB(this)
            listData = data.nowPlaying
            Thread {
                //Do your database´s operations here
                listData!!.forEach {
                    movieDao.insertData(it)
                }
            }.start()



            binding.seeMoreNowPlaying.setOnClickListener {
                val toMovieList = Intent(this, ListMovieActivity::class.java)

                val sharedPreferences: SharedPreferences =
                    this.getSharedPreferences("PREF", Context.MODE_PRIVATE)

                val editor = sharedPreferences.edit()
                val gson = Gson()
                val json = gson.toJson(data.nowPlaying)
                editor.putString("dataMovieByQuery", json)
                editor.commit()

                startActivity(toMovieList)
            }
        })
    }

    private fun observeGenre() {
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
                    searchMovieRepository,
                    sessionRepository
                ) as T
            }
        })[MainActivityViewModel::class.java]
    }

    override fun onBackPressed() {
        showConfirmDialog(
            "Close the App",
            "are you sure want to close the app?",
            "Yes",
            "Cancel",
            onPositifClicked = { closeApp() }
        )

    }

    private fun closeApp() {
        finishAffinity()
        exitProcess(0);
    }
}