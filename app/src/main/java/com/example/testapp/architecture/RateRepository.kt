package com.example.testapp.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp.api.ApiInterface
import com.example.testapp.model.RateMovie
import io.reactivex.disposables.CompositeDisposable

class RateRepository(private val apiService: ApiInterface) {

    lateinit var rateDataSource: RateDataSource

    fun postRateMovie(
        compositeDisposable: CompositeDisposable,
        movieId: Int,
        key: String
    ): MutableLiveData<RateMovie> {

        rateDataSource = RateDataSource(apiService, compositeDisposable)
        rateDataSource.postRateMovie(movieId, key)

        return rateDataSource.rateResponse

    }

    fun getGenreNetworkState(): LiveData<NetworkState> {
        return rateDataSource.networkState
    }
}