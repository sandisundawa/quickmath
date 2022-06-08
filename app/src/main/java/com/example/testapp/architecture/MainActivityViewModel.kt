package com.example.testapp.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.model.MainGenre
import com.example.testapp.model.NowPlayingResult
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(
    private val genreRepository: GenreRepository,
    private val nowPlayingRepository: NowPlayingRepository
) : ViewModel() {

    private val API_KEY = "2c35c921410c9727265ed66192629a38"

    private val compositeDisposable = CompositeDisposable()
    var listGenreData: MutableLiveData<MainGenre>? = null
    var listNowPlayingData: MutableLiveData<NowPlayingResult>? = null

    fun getListGenre() {
        listGenreData = genreRepository.fetchGenre(compositeDisposable, API_KEY)
    }

    fun getListNowPlaying() {
        listNowPlayingData = nowPlayingRepository.fetchNowPlaying(compositeDisposable, API_KEY)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}