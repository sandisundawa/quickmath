package com.moviewers.testapp.testapp.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moviewers.testapp.testapp.api.ApiInterface
import com.moviewers.testapp.testapp.model.Review
import io.reactivex.disposables.CompositeDisposable

class ReviewRepository(private val apiService: ApiInterface) {

    lateinit var reviewDataSource: ReviewDataSource

    fun fetchReviews(
        compositeDisposable: CompositeDisposable,
        movieId: Int,
        key: String
    ): MutableLiveData<Review> {

        reviewDataSource = ReviewDataSource(apiService, compositeDisposable)
        reviewDataSource.fetchReview(movieId, key)

        return reviewDataSource.reviewResponse

    }

    fun getGenreNetworkState(): LiveData<NetworkState> {
        return reviewDataSource.networkState
    }
}