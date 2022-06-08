package com.example.testapp.architecture

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp.api.ApiInterface
import com.example.testapp.model.NowPlayingResult
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NowPlayingDataSource(private val apiService : ApiInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState  = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _nowPlayingResponse =  MutableLiveData<NowPlayingResult>()
    val nowPlayingResponse: MutableLiveData<NowPlayingResult>
        get() = _nowPlayingResponse

    fun fetchNowPlaying(key: String) {

        _networkState.postValue(NetworkState.LOADING)


        try {
            compositeDisposable.add(
                apiService.getNowPlaying(key)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _nowPlayingResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("NowPlayingDataSource", it.message.orEmpty())
                        }
                    )
            )

        }

        catch (e: Exception){
            Log.e("NowPlayingDataSource",e.message.orEmpty())
        }
    }
}