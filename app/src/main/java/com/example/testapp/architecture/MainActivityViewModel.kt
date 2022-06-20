package com.example.testapp.architecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.model.MainGenre
import com.example.testapp.model.Movie
import com.example.testapp.model.NowPlayingResult
import com.example.testapp.model.TrendingResult
import io.reactivex.disposables.CompositeDisposable
import retrofit2.http.Query

class MainActivityViewModel(
    private val genreRepository: GenreRepository,
    private val nowPlayingRepository: NowPlayingRepository,
    private val trendingRepository: TrendingRepository,
    private val searchMovieRepository: SearchMovieRepository,
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var listGenreData: MutableLiveData<MainGenre>? = null
    var listNowPlayingData: MutableLiveData<NowPlayingResult>? = null
    var listTrendingData: MutableLiveData<TrendingResult>? = null
    var listMovieByQuery: MutableLiveData<Movie>? = null

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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}