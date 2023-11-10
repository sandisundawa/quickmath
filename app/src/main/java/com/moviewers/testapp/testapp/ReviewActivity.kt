package com.moviewers.testapp.testapp

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.moviewers.testapp.testapp.adapter.ReviewAdapter
import com.moviewers.testapp.testapp.architecture.ReviewViewModel
import com.moviewers.testapp.testapp.databinding.ActivityReviewBinding

private lateinit var binding: ActivityReviewBinding

class ReviewActivity : BaseActivity() {

    private lateinit var viewModel: ReviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)

        val idMovie = intent.getStringExtra("idMovie")
        viewModel.getReviews(getString(R.string.api_key), idMovie.orEmpty().toInt())

        setData()

    }

    private fun setData() {
        viewModel.listReview?.observe(this, Observer { data ->
            binding.loadingPanel.visibility = View.GONE
            val reviewAdapter = ReviewAdapter(data.results.orEmpty(), this)
            binding.rvReviews.adapter = reviewAdapter
            binding.rvReviews.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        })
    }
}