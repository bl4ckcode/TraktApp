package com.bl4ckcode.traktapp.presenter

import android.content.Context
import android.content.Intent
import com.bl4ckcode.traktapp.activity.DetailsActivity
import com.bl4ckcode.traktapp.interactor.MainInteractor
import com.bl4ckcode.traktapp.interactor.MainInteractorDelegate
import com.bl4ckcode.traktapp.model.Movie
import com.bl4ckcode.traktapp.util.Constants.Companion.MOVIE_DETAILS_ARG

interface MainPresenterDelegate {
    fun setMoviesList(movies: List<Movie>?)
}

class MainPresenter(private val delegate: MainPresenterDelegate) : MainInteractorDelegate {

    private val mPresenterInteractorDelegate = MainInteractor(this)

    fun retrieveMoviesList(page: Int) {
        mPresenterInteractorDelegate.getPopularMoviesList(page)
    }

    fun goToDetailsActivity(context: Context, movie: Movie) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra(MOVIE_DETAILS_ARG, movie)
        context.startActivity(intent)
    }

    override fun setPopularMovies(movies: List<Movie>?) {
        delegate.setMoviesList(movies)
    }
}