package com.bl4ckcode.traktapp.service

import com.bl4ckcode.traktapp.model.Movie
import com.bl4ckcode.traktapp.util.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface TraktService {
    @Headers(
        "Content-type: application/json",
        "trakt-api-key: " + Constants.TraktClientID,
        "trakt-api-version: 2",
        "X-Pagination-Limit: 100",
        "X-Pagination-Page-Count: 100",
        "X-Pagination-Item-Count: 100"
    )
    @GET("movies/popular")
    fun popularMovies(@Header("X-Pagination-Page") page: Int): Call<List<Movie>>

    @Headers(
        "Content-type: application/json",
        "trakt-api-key: " + Constants.TraktClientID,
        "trakt-api-version: 2"
    )
    @GET("movies/{id}?extended=full")
    fun movieDetails(@Path("id") movieId: String): Call<Movie>
}