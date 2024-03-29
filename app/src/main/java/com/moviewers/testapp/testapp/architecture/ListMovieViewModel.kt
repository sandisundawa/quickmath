package com.moviewers.testapp.testapp.architecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviewers.testapp.testapp.api.ApiClient
import com.moviewers.testapp.testapp.model.Movie
import io.reactivex.disposables.CompositeDisposable

class ListMovieViewModel(
    private val listMovieByGenreRepository: ListMovieByGenreRepository = ListMovieByGenreRepository(
        ApiClient.getClient()
    )
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var listMovie: MutableLiveData<Movie>? = null

    fun getDiscoverMovie(
        apiKey: String,
        genre: String? = null,
        sortBy: String? = null,
        region: String? = null
    ) {
        listMovie = listMovieByGenreRepository.fetchMovies(
            compositeDisposable,
            apiKey,
            genre.orEmpty(),
            sortBy.orEmpty(),
            region.orEmpty()
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}