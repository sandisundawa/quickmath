package com.moviewers.testapp.testapp.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moviewers.testapp.testapp.api.ApiInterface
import com.moviewers.testapp.testapp.model.NowPlayingResult
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