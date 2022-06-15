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

    fun getListMovieByGenre(apiKey: String, genre: String) {
        listMovie = listMovieByGenreRepository.fetchMovies(compositeDisposable, apiKey, genre)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}