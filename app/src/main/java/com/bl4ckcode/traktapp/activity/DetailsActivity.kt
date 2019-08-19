package com.bl4ckcode.traktapp.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bl4ckcode.traktapp.R
import com.bl4ckcode.traktapp.model.Movie
import com.bl4ckcode.traktapp.presenter.DetailsPresenter
import com.bl4ckcode.traktapp.presenter.DetailsPresenterDelegate
import com.bl4ckcode.traktapp.util.Constants

class DetailsActivity : AppCompatActivity(), DetailsPresenterDelegate {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val movie = intent.getParcelableExtra<Movie>(Constants.MOVIE_DETAILS_ARG)

        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        mToolbar.title = movie.title
        setSupportActionBar(mToolbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val mPresenterDelegate = DetailsPresenter(this)
        mPresenterDelegate.retrieveMovieDetails(movie)
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setMovieDetails(movie: Movie?) {
        val mTxtViewTagline = findViewById<TextView>(R.id.txtView_tagline_desc)
        val mTxtViewOverview = findViewById<TextView>(R.id.txtView_overview_desc)
        val mTxtViewReleased = findViewById<TextView>(R.id.txtView_released_desc)

        mTxtViewTagline.text = movie?.tagline
        mTxtViewOverview.text = movie?.overview
        mTxtViewReleased.text = movie?.released
    }
}
