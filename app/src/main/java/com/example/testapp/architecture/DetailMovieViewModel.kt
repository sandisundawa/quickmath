package com.example.testapp.architecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.model.DetailMovie
import com.example.testapp.model.RateMovie
import com.example.testapp.model.RatingValue
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable

class DetailMovieViewModel(
    private val detailMovieRepository: DetailMovieRepository,
    private val rateRepository: RateRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var dataMovie: MutableLiveData<DetailMovie>? = null
    var rateMovie: MutableLiveData<RateMovie>? = null

    fun getMovieDetail(apiKey: String, movieId: Int) {
        dataMovie = detailMovieRepository.fetchMovieId(compositeDisposable, movieId, apiKey)
    }

    fun postRating(apiKey: String, movieId: Int, sessionId: String, body: JsonObject) {
        rateMovie = rateRepository.postRateMovie(compositeDisposable, movieId, apiKey, sessionId, body)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}