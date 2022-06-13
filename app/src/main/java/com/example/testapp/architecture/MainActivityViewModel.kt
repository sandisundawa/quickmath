package com.example.testapp.architecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.model.MainGenre
import com.example.testapp.model.NowPlayingResult
import com.example.testapp.model.TrendingResult
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(
    private val genreRepository: GenreRepository,
    private val nowPlayingRepository: NowPlayingRepository,
    private val trendingRepository: TrendingRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var listGenreData: MutableLiveData<MainGenre>? = null
    var listNowPlayingData: MutableLiveData<NowPlayingResult>? = null
    var listTrendingData: MutableLiveData<TrendingResult>? = null

    fun getListGenre(apiKey: String) {
        listGenreData = genreRepository.fetchGenre(compositeDisposable, apiKey)
    }

    fun getListNowPlaying(apiKey: String) {
        listNowPlayingData = nowPlayingRepository.fetchNowPlaying(compositeDisposable, apiKey)
    }

    fun getTrending(apiKey: String) {
        listTrendingData = trendingRepository.fetchTrending(compositeDisposable, apiKey)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}