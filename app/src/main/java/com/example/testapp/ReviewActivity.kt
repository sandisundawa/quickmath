package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.adapter.MoviesAdapter
import com.example.testapp.adapter.ReviewAdapter
import com.example.testapp.architecture.ListMovieViewModel
import com.example.testapp.architecture.ReviewRepository
import com.example.testapp.architecture.ReviewViewModel
import com.example.testapp.databinding.ActivityReviewBinding

private lateinit var binding: ActivityReviewBinding

private lateinit var viewModel: ReviewViewModel
lateinit var repository: ReviewRepository

class ReviewActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = ReviewRepository(apiService)
        viewModel = getViewModel()

        val idMovie = intent.getStringExtra("idMovie")
        viewModel.getReviews(getString(R.string.api_key), idMovie.orEmpty().toInt())

        setData()

    }

    private fun setData() {
        viewModel.listReview?.observe(this, Observer { data ->
            val reviewAdapter = ReviewAdapter(data.results.orEmpty(), this)
            binding.rvReviews.adapter = reviewAdapter
            binding.rvReviews.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        })
    }

    private fun getViewModel(): ReviewViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ReviewViewModel(repository) as T
            }
        })[ReviewViewModel::class.java]
    }
}