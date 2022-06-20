package com.example.testapp.api

import com.example.testapp.model.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("genre/movie/list")
    fun getGenre(@Query("api_key") apiKey: String?): Observable<MainGenre?>

    @GET("discover/movie")
    fun getMovie(
        @Query("api_key") apiKey: String?,
        @Query("with_genres") withGenres: String?
    ): Observable<Movie?>

    @GET("search/movie")
    fun getMovieBySearch(
        @Query("api_key") apiKey: String?,
        @Query("query") query: String?
    ): Observable<Movie?>

    @GET("movie/{movie_id}")
    fun getDetail(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String?
    ): Observable<DetailMovie?>

    @GET("movie/{movie_id}/reviews")
    fun getReview(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String?
    ): Observable<Review?>

    @GET("movie/now_playing")
    fun getNowPlaying(
        @Query("api_key") apiKey: String?
    ): Observable<NowPlayingResult?>

    @GET("trending/{media_type}/{time_window}")
    fun getTrending(
        @Path("media_type") mediaType: String,
        @Path("time_window") timeWindow: String,
        @Query("api_key") apiKey: String
    ): Observable<TrendingResult>

}