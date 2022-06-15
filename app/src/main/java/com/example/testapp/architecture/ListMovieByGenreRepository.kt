package com.example.testapp.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp.api.ApiInterface
import com.example.testapp.model.Movie
import io.reactivex.disposables.CompositeDisposable

class ListMovieByGenreRepository (private val apiService : ApiInterface) {

    lateinit var listMovieByGenreDataSource: ListMovieByGenreDataSource

    fun fetchMovies (compositeDisposable: CompositeDisposable, key: String, genre: String) : MutableLiveData<Movie> {

        listMovieByGenreDataSource = ListMovieByGenreDataSource(apiService,compositeDisposable)
        listMovieByGenreDataSource.fetchMoviesByGanre(key, genre)

        return listMovieByGenreDataSource.movieResponse
    }

    fun getGenreNetworkState(): LiveData<NetworkState> {
        return listMovieByGenreDataSource.networkState
    }
}