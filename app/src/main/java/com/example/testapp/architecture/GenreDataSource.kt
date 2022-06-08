package com.example.testapp.architecture

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp.api.ApiInterface
import com.example.testapp.model.MainGenre
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GenreDataSource (private val apiService : ApiInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState  = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _genreResponse =  MutableLiveData<MainGenre>()
    val genreResponse: MutableLiveData<MainGenre>
        get() = _genreResponse

    fun fetchGenre(key: String) {

        _networkState.postValue(NetworkState.LOADING)


        try {
            compositeDisposable.add(
                apiService.getGenre(key)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _genreResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("GenreDataSource", it.message.orEmpty())
                        }
                    )
            )

        }

        catch (e: Exception){
            Log.e("GenreDataSource",e.message.orEmpty())
        }
    }
}