package com.example.testapp.architecture

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp.api.ApiInterface
import com.example.testapp.model.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListMovieByGenreDataSource(
    private val apiService: ApiInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _movieResponse = MutableLiveData<Movie>()
    val movieResponse: MutableLiveData<Movie>
        get() = _movieResponse

    fun fetchDiscoverMovie(key: String, genre: String, sortBy: String, region: String) {

        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovie(key, genre, sortBy, region)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _movieResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("MoviesDataSource", it.message.orEmpty())
                        }
                    )
            )

        } catch (e: Exception) {
            Log.e("MoviesDataSource", e.message.orEmpty())
        }
    }
}