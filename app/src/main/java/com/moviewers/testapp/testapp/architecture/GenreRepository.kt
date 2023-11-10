package com.moviewers.testapp.testapp.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moviewers.testapp.testapp.api.ApiInterface
import com.moviewers.testapp.testapp.model.MainGenre
import io.reactivex.disposables.CompositeDisposable

class GenreRepository (private val apiService : ApiInterface) {

    lateinit var genreDataSource: GenreDataSource

    fun fetchGenre (compositeDisposable: CompositeDisposable, key: String) : MutableLiveData<MainGenre> {

        genreDataSource = GenreDataSource(apiService,compositeDisposable)
        genreDataSource.fetchGenre(key)

        return genreDataSource.genreResponse
    }

    fun getGenreNetworkState(): LiveData<NetworkState> {
        return genreDataSource.networkState
    }
}