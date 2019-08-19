package com.bl4ckcode.traktapp.presenter

import com.bl4ckcode.traktapp.interactor.DetailsInteractor
import com.bl4ckcode.traktapp.interactor.DetailsInteractorDelegate
import com.bl4ckcode.traktapp.model.Movie

interface DetailsPresenterDelegate {
    fun setMovieDetails(movie: Movie?)
}

class DetailsPresenter(private val delegate: DetailsPresenterDelegate) : DetailsInteractorDelegate {
    private val mPresenterInteractorDelegate = DetailsInteractor(this)

    fun retrieveMovieDetails(movie: Movie) {
        mPresenterInteractorDelegate.getMovieDetails(movie)
    }

    override fun setMovieDetails(movie: Movie?) {
        delegate.setMovieDetails(movie)
    }
}