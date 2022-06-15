package com.example.testapp.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp.api.ApiInterface
import com.example.testapp.model.TrendingResult
import io.reactivex.disposables.CompositeDisposable

class TrendingRepository(private val apiService: ApiInterface) {

    lateinit var trendingDataSource: TrendingDataSource

    fun fetchTrending(
        compositeDisposable: CompositeDisposable,
        key: String
    ): MutableLiveData<TrendingResult> {

        trendingDataSource = TrendingDataSource(apiService, compositeDisposable)
        trendingDataSource.fetchTrending(key, "movie", "day")

        return trendingDataSource.trendingResponse

    }

    fun getGenreNetworkState(): LiveData<NetworkState> {
        return trendingDataSource.networkState
    }
}