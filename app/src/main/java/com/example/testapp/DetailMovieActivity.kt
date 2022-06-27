package com.example.testapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.testapp.architecture.DetailMovieRepository
import com.example.testapp.architecture.DetailMovieViewModel
import com.example.testapp.architecture.RateRepository
import com.example.testapp.databinding.ActivityDetailMovieBinding
import com.google.gson.JsonObject
import com.orhanobut.hawk.Hawk


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

        Hawk.init(this).build()
        val sessionId: String = Hawk.get("sessionId")

        detailMovieRepository = DetailMovieRepository(apiService)
        rateRepository = RateRepository(apiService)
        viewModel = getViewModel()

        val idMovie = intent.getStringExtra("idMovie")
        viewModel.getMovieDetail(getString(R.string.api_key), idMovie.orEmpty().toInt())
        setData()

        binding.btnRating.setOnClickListener {
            dialogRating(idMovie.orEmpty(), sessionId)
        }

    }

    private fun dialogRating(idMovie: String, sessionId: String) {
        val builder = AlertDialog.Builder(this)
            .create()
        val view = layoutInflater.inflate(R.layout.rating_dialog, null)
        val slider = view.findViewById<SeekBar>(R.id.slider_rating)
        val textValue = view.findViewById<TextView>(R.id.value_rating)
        val btnSubmit = view.findViewById<Button>(R.id.btn_submit)

        slider.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val doubledValue: Double = (slider.progress / 2.0)
                textValue.text = "$doubledValue/10"

                val reqBody = JsonObject()
                reqBody.addProperty("value", doubledValue)

                btnSubmit.setOnClickListener {

                    viewModel.postRating(
                        getString(R.string.api_key),
                        idMovie.toInt(),
                        sessionId,
                        reqBody.asJsonObject
                    )
                    observeRating()
                    builder.dismiss()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onProgressChanged(
                seekBar: SeekBar,
                progressValue: Int,
                fromUser: Boolean
            ) {
                val doubledValue: Double = (slider.progress / 2.0)
                textValue.text = "$doubledValue/10"
            }
        })

        builder.setCancelable(true)
        builder.setView(view)
        builder.show()
    }

    private fun observeRating() {
        viewModel.rateMovie?.observe(this, Observer { data ->
            Log.d("status", "" + data.status_code + data.status_message + "")
            if (data.status_code == 1) {
                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Submitted")
                    .show()
            } else if (data.status_code == 12) {
                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Data Updated")
                    .show()
            }
        })
    }

    private fun setData() {
        val baseImage = "https://image.tmdb.org/t/p/w500"

        viewModel.dataMovie?.observe(this, Observer { data ->
            binding.loadingPanel.visibility = View.GONE
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