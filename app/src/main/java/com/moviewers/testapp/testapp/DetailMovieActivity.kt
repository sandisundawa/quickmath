package com.moviewers.testapp.testapp

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
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.moviewers.testapp.testapp.architecture.*
import com.moviewers.testapp.testapp.databinding.ActivityDetailMovieBinding
import com.moviewers.testapp.testapp.di.ApiKey
import com.moviewers.testapp.testapp.di.DaggerComponent
import com.google.gson.JsonObject
import com.orhanobut.hawk.Hawk
import javax.inject.Inject


private lateinit var binding: ActivityDetailMovieBinding

class DetailMovieActivity : BaseActivity() {

    private lateinit var viewModel: DetailMovieViewModel
    @Inject
    lateinit var apiKey: ApiKey

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel = ViewModelProvider(this).get(DetailMovieViewModel::class.java)
        DaggerComponent.create().injectDetail(this)

        Hawk.init(this).build()
        val sessionId: String = Hawk.get("sessionId")

        val idMovie = intent.getStringExtra("idMovie")
        viewModel.getMovieDetail(apiKey.getApiKey(), idMovie.orEmpty().toInt())
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
        viewModel.dataMovie?.observe(this, Observer { data ->
            binding.tvTitleMovie.text = data.title
            binding.tvDesc.text = data.overview
            binding.tvRating.text = data.voteAverage.toString()
            binding.tvDuration.text = "Duration : " + data.runtime.toString() + " minutes"

            if (data.posterPath != null) {
                val requestOptions = RequestOptions()
                Glide.with(this)
                    .load(getString(R.string.base_image) + data.posterPath)
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

        viewModel.networkState?.observe(this, Observer {
            if (it.status == Status.FAILED) {
                binding.loadingPanel.visibility = View.GONE
                Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
            }
        })
    }
}