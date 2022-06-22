package com.example.testapp.architecture

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp.api.ApiInterface
import com.example.testapp.model.RateMovie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RateDataSource(
    private val apiService: ApiInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _rateResponse = MutableLiveData<RateMovie>()
    val rateResponse: MutableLiveData<RateMovie>
        get() = _rateResponse

    fun postRateMovie(movieId: Int, key: String) {

        _networkState.postValue(NetworkState.LOADING)


        try {
            compositeDisposable.add(
                apiService.postRateMovie(movieId, key)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _rateResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("rateDataSource", it.message.orEmpty())
                        }
                    )
            )

        } catch (e: Exception) {
            Log.e("rateDataSource", e.message.orEmpty())
        }
    }
}