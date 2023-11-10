package com.moviewers.testapp.testapp.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moviewers.testapp.testapp.api.ApiInterface
import com.moviewers.testapp.testapp.model.Movie
import io.reactivex.disposables.CompositeDisposable

class ListMovieByGenreRepository(private val apiService: ApiInterface) {

    lateinit var listMovieByGenreDataSource: ListMovieByGenreDataSource

    fun fetchMovies(
        compositeDisposable: CompositeDisposable,
        key: String,
        genre: String,
        sortBy: String,
        region: String
    ): MutableLiveData<Movie> {

        listMovieByGenreDataSource = ListMovieByGenreDataSource(apiService, compositeDisposable)
        listMovieByGenreDataSource.fetchDiscoverMovie(key, genre, sortBy, region)

        return listMovieByGenreDataSource.movieResponse
    }

    fun getGenreNetworkState(): LiveData<NetworkState> {
        return listMovieByGenreDataSource.networkState
    }
}