package com.example.testapp.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp.api.ApiInterface
import com.example.testapp.model.DetailMovie
import io.reactivex.disposables.CompositeDisposable

class DetailMovieRepository(private val apiService : ApiInterface) {

    lateinit var detailMovieDataSource: DetailMovieDataSource

    fun fetchMovieId (compositeDisposable: CompositeDisposable, movieId: Int, key: String) : MutableLiveData<DetailMovie> {

        detailMovieDataSource = DetailMovieDataSource(apiService,compositeDisposable)
        detailMovieDataSource.fetchDetailMovie(movieId,key)

        return detailMovieDataSource.detailMovieResponse
    }

    fun getGenreNetworkState(): LiveData<NetworkState> {
        return detailMovieDataSource.networkState
    }
}