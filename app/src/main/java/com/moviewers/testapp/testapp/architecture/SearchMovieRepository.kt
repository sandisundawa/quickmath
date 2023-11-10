package com.moviewers.testapp.testapp.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moviewers.testapp.testapp.api.ApiInterface
import com.moviewers.testapp.testapp.model.Movie
import io.reactivex.disposables.CompositeDisposable

class SearchMovieRepository(private val apiService : ApiInterface) {

    lateinit var searchMovieDataSource: SearchMovieDataSource

    fun fetchMovies(compositeDisposable: CompositeDisposable, key: String, query: String) : MutableLiveData<Movie> {

        searchMovieDataSource = SearchMovieDataSource(apiService,compositeDisposable)
        searchMovieDataSource.fetchMoviesByQuery(key, query)

        return searchMovieDataSource.movieResponse
    }

    fun getGenreNetworkState(): LiveData<NetworkState> {
        return searchMovieDataSource.networkState
    }
}