package com.example.testapp.architecture

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp.api.ApiInterface
import com.example.testapp.model.TrendingResult
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TrendingDataSource(private val apiService : ApiInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState  = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _trendingResponse =  MutableLiveData<TrendingResult>()
    val trendingResponse: MutableLiveData<TrendingResult>
        get() = _trendingResponse

    fun fetchTrending(key: String, type: String, time: String) {

        _networkState.postValue(NetworkState.LOADING)


        try {
            compositeDisposable.add(
                apiService.getTrending(type, time, key)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _trendingResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("TrendingDataSource", it.message.orEmpty())
                        }
                    )
            )

        }

        catch (e: Exception){
            Log.e("TrendingDataSource",e.message.orEmpty())
        }
    }
}