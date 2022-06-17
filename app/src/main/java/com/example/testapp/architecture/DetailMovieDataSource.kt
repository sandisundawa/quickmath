package com.example.testapp.architecture

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp.api.ApiInterface
import com.example.testapp.model.DetailMovie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailMovieDataSource(
    private val apiService: ApiInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _detailMovieResponse = MutableLiveData<DetailMovie>()
    val detailMovieResponse: MutableLiveData<DetailMovie>
        get() = _detailMovieResponse

    fun fetchDetailMovie(movieId: Int, key: String) {

        _networkState.postValue(NetworkState.LOADING)


        try {
            compositeDisposable.add(
                apiService.getDetail(movieId, key)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _detailMovieResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDataSource", it.message.orEmpty())
                        }
                    )
            )

        } catch (e: Exception) {
            Log.e("MovieDataSource", e.message.orEmpty())
        }
    }
}