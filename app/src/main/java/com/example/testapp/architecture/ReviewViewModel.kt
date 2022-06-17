package com.example.testapp.architecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.model.Review
import io.reactivex.disposables.CompositeDisposable

class ReviewViewModel(private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var listReview: MutableLiveData<Review>? = null

    fun getReviews(apiKey: String, movieId: Int) {
        listReview = reviewRepository.fetchReviews(compositeDisposable, movieId, apiKey)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}