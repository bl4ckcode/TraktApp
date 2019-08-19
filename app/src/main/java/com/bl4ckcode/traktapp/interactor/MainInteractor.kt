package com.bl4ckcode.traktapp.interactor

import com.bl4ckcode.traktapp.model.Movie
import com.bl4ckcode.traktapp.router.TraktService
import com.bl4ckcode.traktapp.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface MainInteractorDelegate {
    fun setPopularMovies(movies: List<Movie>?)
}

class MainInteractor (private var delegate: MainInteractorDelegate) {

    fun getPopularMoviesList(page: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.TraktBaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val traktService = retrofit.create(TraktService::class.java)

        traktService.popularMovies(page).enqueue(object : Callback<List<Movie>> {
            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                delegate.setPopularMovies(null)
            }

            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                if (response.isSuccessful) {
                    delegate.setPopularMovies(response.body())
                }
            }
        })
    }
}