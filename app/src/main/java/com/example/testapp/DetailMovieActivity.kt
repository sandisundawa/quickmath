package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.testapp.architecture.DetailMovieRepository
import com.example.testapp.architecture.DetailMovieViewModel
import com.example.testapp.architecture.ListMovieByGenreRepository
import com.example.testapp.architecture.ListMovieViewModel
import com.example.testapp.databinding.ActivityDetailMovieBinding
import com.example.testapp.databinding.ActivityListMovieBinding

private lateinit var binding: ActivityDetailMovieBinding

private lateinit var viewModel: DetailMovieViewModel
lateinit var detailMovieRepository: DetailMovieRepository

class DetailMovieActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        detailMovieRepository = DetailMovieRepository(apiService)
        viewModel = getViewModel()

        val idMovie = intent.getStringExtra("idMovie")
        viewModel.getListMovieByGenre(getString(R.string.api_key), idMovie.orEmpty().toInt())
        setData()
    }

    private fun setData() {
        val baseImage = "https://image.tmdb.org/t/p/w500"

        viewModel.dataMovie?.observe(this, Observer { data ->
            binding.tvTitleMovie.text = data.title
            binding.tvDesc.text = data.overview
            binding.tvRating.text = data.voteAverage.toString()
            binding.tvDuration.text = "Duration :" +data.runtime.toString()+" minutes"

            if (data.posterPath != null) {
                val requestOptions = RequestOptions()
                Glide.with(this)
                    .load(baseImage + data.posterPath)
                    .apply(requestOptions).into(binding.ivPoster)
            }

            binding.btnReview.setOnClickListener {
                val toReview = Intent(this, ReviewActivity::class.java)
                toReview.putExtra("idMovie", data.id.toString())
                startActivity(toReview)
            }

        })
    }

    private fun getViewModel(): DetailMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return DetailMovieViewModel(detailMovieRepository) as T
            }
        })[DetailMovieViewModel::class.java]
    }
}