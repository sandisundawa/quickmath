package com.example.testapp.architecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.model.DetailMovie
import io.reactivex.disposables.CompositeDisposable

class DetailMovieViewModel(private val detailMovieRepository: DetailMovieRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var dataMovie: MutableLiveData<DetailMovie>? = null

    fun getListMovieByGenre(apiKey: String, movieId: Int) {
        dataMovie = detailMovieRepository.fetchMovieId(compositeDisposable, movieId, apiKey)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}