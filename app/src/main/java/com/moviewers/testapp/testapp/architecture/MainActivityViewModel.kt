package com.moviewers.testapp.testapp.architecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviewers.testapp.testapp.api.ApiClient
import com.moviewers.testapp.testapp.model.*
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(
    private val genreRepository: GenreRepository = GenreRepository(ApiClient.getClient()),
    private val nowPlayingRepository: NowPlayingRepository = NowPlayingRepository(ApiClient.getClient()),
    private val trendingRepository: TrendingRepository = TrendingRepository(ApiClient.getClient()),
    private val searchMovieRepository: SearchMovieRepository = SearchMovieRepository(ApiClient.getClient()),
    private val sessionRepository: SessionRepository = SessionRepository(ApiClient.getClient())
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var listGenreData: MutableLiveData<MainGenre>? = null
    var listNowPlayingData: MutableLiveData<NowPlayingResult>? = null
    var listTrendingData: MutableLiveData<TrendingResult>? = null
    var listMovieByQuery: MutableLiveData<Movie>? = null
    var sessionData: MutableLiveData<SessionResult>? = null

    fun getListGenre(apiKey: String) {
        listGenreData = genreRepository.fetchGenre(compositeDisposable, apiKey)
    }

    fun getListNowPlaying(apiKey: String) {
        listNowPlayingData = nowPlayingRepository.fetchNowPlaying(compositeDisposable, apiKey)
    }

    fun getTrending(apiKey: String) {
        listTrendingData = trendingRepository.fetchTrending(compositeDisposable, apiKey)
    }

    fun getMovieByQuery(apiKey: String, query: String) {
        listMovieByQuery = searchMovieRepository.fetchMovies(compositeDisposable, apiKey, query)
    }

    fun getSession(apiKey: String) {
        sessionData = sessionRepository.getSession(compositeDisposable, apiKey)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}