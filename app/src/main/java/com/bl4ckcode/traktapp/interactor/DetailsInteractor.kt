package com.bl4ckcode.traktapp.interactor

import com.bl4ckcode.traktapp.model.Movie
import com.bl4ckcode.traktapp.service.TraktService
import com.bl4ckcode.traktapp.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface DetailsInteractorDelegate {
    fun setMovieDetails(movie: Movie?)
}

class DetailsInteractor (private var delegate: DetailsInteractorDelegate) {

    fun getMovieDetails(movie: Movie) {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.TraktBaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val traktService = retrofit.create(TraktService::class.java)

        traktService.movieDetails(movie.ids.getValue(Constants.MOVIE_ID_ARG)).enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                delegate.setMovieDetails(null)
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    delegate.setMovieDetails(response.body())
                }
            }

        })
    }
}