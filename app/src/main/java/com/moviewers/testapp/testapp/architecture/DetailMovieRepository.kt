package com.moviewers.testapp.testapp.architecture

import androidx.lifecycle.MutableLiveData
import com.moviewers.testapp.testapp.api.ApiInterface
import com.moviewers.testapp.testapp.model.DetailMovie
import io.reactivex.disposables.CompositeDisposable

class DetailMovieRepository(private val apiService : ApiInterface) {

    lateinit var detailMovieDataSource: DetailMovieDataSource

    fun fetchMovieId (compositeDisposable: CompositeDisposable, movieId: Int, key: String) : MutableLiveData<DetailMovie> {

        detailMovieDataSource = DetailMovieDataSource(apiService,compositeDisposable)
        detailMovieDataSource.fetchDetailMovie(movieId,key)

        return detailMovieDataSource.detailMovieResponse
    }

    fun getDetailNetworkState(): MutableLiveData<NetworkState> {
        return detailMovieDataSource.networkState
    }
}