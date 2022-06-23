package com.example.testapp.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp.api.ApiInterface
import com.example.testapp.model.RateMovie
import com.example.testapp.model.RatingValue
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable

class RateRepository(private val apiService: ApiInterface) {

    lateinit var rateDataSource: RateDataSource

    fun postRateMovie(
        compositeDisposable: CompositeDisposable,
        movieId: Int,
        key: String,
        sessionId: String,
        body: JsonObject
    ): MutableLiveData<RateMovie> {

        rateDataSource = RateDataSource(apiService, compositeDisposable)
        rateDataSource.postRateMovie(movieId, key, sessionId, body)

        return rateDataSource.rateResponse

    }

    fun getGenreNetworkState(): LiveData<NetworkState> {
        return rateDataSource.networkState
    }
}