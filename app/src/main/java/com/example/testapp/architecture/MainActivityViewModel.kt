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

    private val compositeDisposable = CompositeDisposable()
    var listGenreData: MutableLiveData<MainGenre>? = null
    var listNowPlayingData: MutableLiveData<NowPlayingResult>? = null

    fun getListGenre(apiKey: String) {
        listGenreData = genreRepository.fetchGenre(compositeDisposable, apiKey)
    }

    fun getListNowPlaying(apiKey: String) {
        listNowPlayingData = nowPlayingRepository.fetchNowPlaying(compositeDisposable, apiKey)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}