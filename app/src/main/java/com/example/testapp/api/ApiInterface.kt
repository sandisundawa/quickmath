package com.example.testapp.api

import com.example.testapp.model.DetailMovie
import com.example.testapp.model.MainGenre
import com.example.testapp.model.Movie
import com.example.testapp.model.Review
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    //genre
    @GET("genre/movie/list")
    fun getGenre(@Query("api_key") apiKey: String?): Observable<MainGenre?>

    @GET("discover/movie")
    fun getMovie(
        @Query("api_key") apiKey: String?,
        @Query("with_genres") withGenres: String?
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

}