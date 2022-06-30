package com.example.testapp.architecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.model.Movie
import io.reactivex.disposables.CompositeDisposable

class ListMovieViewModel(
    private val listMovieByGenreRepository: ListMovieByGenreRepository
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