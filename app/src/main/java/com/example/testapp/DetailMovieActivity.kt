package com.example.testapp

import android.R.attr.textSize
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.testapp.architecture.DetailMovieRepository
import com.example.testapp.architecture.DetailMovieViewModel
import com.example.testapp.architecture.RateRepository
import com.example.testapp.databinding.ActivityDetailMovieBinding


private lateinit var binding: ActivityDetailMovieBinding

private lateinit var viewModel: DetailMovieViewModel
lateinit var detailMovieRepository: DetailMovieRepository
lateinit var rateRepository: RateRepository

class DetailMovieActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        detailMovieRepository = DetailMovieRepository(apiService)
        rateRepository = RateRepository(apiService)
        viewModel = getViewModel()

        val idMovie = intent.getStringExtra("idMovie")
        viewModel.getMovieDetail(getString(R.string.api_key), idMovie.orEmpty().toInt())
        setData()

        binding.btnRating.setOnClickListener {
            //show slider
            dialogRating()
        }
    }

    private fun dialogRating() {
        val builder = AlertDialog.Builder(this)
            .create()
        val view = layoutInflater.inflate(R.layout.rating_dialog, null)
        val slider = view.findViewById<SeekBar>(R.id.slider_rating)
        val textValue = view.findViewById<TextView>(R.id.value_rating)

        slider.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            var progress = 0
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val doubledValue: Double = (slider.progress / 10.0)
                textValue.text = "$doubledValue/10"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onProgressChanged(
                seekBar: SeekBar,
                progressValue: Int,
                fromUser: Boolean
            ) {
                // do something
            }
        })

        builder.setCancelable(true)
        builder.setView(view)
        builder.show()
    }

    private fun setData() {
        val baseImage = "https://image.tmdb.org/t/p/w500"

        viewModel.dataMovie?.observe(this, Observer { data ->
            binding.tvTitleMovie.text = data.title
            binding.tvDesc.text = data.overview
            binding.tvRating.text = data.voteAverage.toString()
            binding.tvDuration.text = "Duration : " + data.runtime.toString() + " minutes"

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

            binding.btnTrailer.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEARCH)
                intent.setPackage("com.google.android.youtube")
                intent.putExtra("query", data.title + " trailer")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }

        })
    }

    private fun getViewModel(): DetailMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return DetailMovieViewModel(detailMovieRepository, rateRepository) as T
            }
        })[DetailMovieViewModel::class.java]
    }
}