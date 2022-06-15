package com.example.testapp.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp.api.ApiInterface
import com.example.testapp.model.NowPlayingResult
import io.reactivex.disposables.CompositeDisposable

class NowPlayingRepository(private val apiService: ApiInterface) {

    lateinit var nowPlayingDataSource: NowPlayingDataSource

    fun fetchNowPlaying(
        compositeDisposable: CompositeDisposable,
        key: String
    ): MutableLiveData<NowPlayingResult> {

        nowPlayingDataSource = NowPlayingDataSource(apiService, compositeDisposable)
        nowPlayingDataSource.fetchNowPlaying(key)

        return nowPlayingDataSource.nowPlayingResponse

    }

    fun getGenreNetworkState(): LiveData<NetworkState> {
        return nowPlayingDataSource.networkState
    }
}