package com.moviewers.testapp.testapp.api

import com.moviewers.testapp.testapp.model.*
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.*

interface ApiInterface {

    @GET("genre/movie/list")
    fun getGenre(@Query("api_key") apiKey: String?): Observable<MainGenre?>

    @GET("discover/movie")
    fun getMovie(
        @Query("api_key") apiKey: String?,
        @Query("with_genres") withGenres: String? = null,
        @Query("sort_by") sortBy: String? = null,
        @Query("region") region: String? = null
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

    @GET("authentication/guest_session/new")
    fun getSession(
        @Query("api_key") apiKey: String?
    ): Observable<SessionResult>


    @Headers("Content-Type: application/json")
    @POST("movie/{movie_id}/rating")
    fun postRateMovie(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String,
        @Query("guest_session_id") sessionId: String,
        @Body body: JsonObject
    ): Observable<RateMovie>

}