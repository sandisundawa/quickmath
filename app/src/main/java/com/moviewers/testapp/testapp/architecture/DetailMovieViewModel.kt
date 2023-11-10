package com.moviewers.testapp.testapp.architecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviewers.testapp.testapp.api.ApiClient
import com.moviewers.testapp.testapp.model.DetailMovie
import com.moviewers.testapp.testapp.model.RateMovie
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable

class DetailMovieViewModel(
    private val detailMovieRepository: DetailMovieRepository = DetailMovieRepository(ApiClient.getClient()),
    private val rateRepository: RateRepository = RateRepository(ApiClient.getClient())
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var dataMovie: MutableLiveData<DetailMovie>? = null
    var rateMovie: MutableLiveData<RateMovie>? = null
    var networkState: MutableLiveData<NetworkState>? = null

    fun getMovieDetail(apiKey: String, movieId: Int) {
        dataMovie = detailMovieRepository.fetchMovieId(compositeDisposable, movieId, apiKey)
        networkState = detailMovieRepository.getDetailNetworkState()
    }

    fun postRating(apiKey: String, movieId: Int, sessionId: String, body: JsonObject) {
        rateMovie = rateRepository.postRateMovie(compositeDisposable, movieId, apiKey, sessionId, body)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}