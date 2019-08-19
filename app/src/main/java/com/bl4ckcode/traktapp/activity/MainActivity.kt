package com.bl4ckcode.traktapp.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bl4ckcode.traktapp.R
import com.bl4ckcode.traktapp.adapter.MainListAdapter
import com.bl4ckcode.traktapp.adapter.OnAdapterListItemListener
import com.bl4ckcode.traktapp.model.Movie
import com.bl4ckcode.traktapp.presenter.MainPresenter
import com.bl4ckcode.traktapp.presenter.MainPresenterDelegate

class MainActivity : AppCompatActivity(), MainPresenterDelegate, OnAdapterListItemListener {

    private var filtering: Boolean = false
    private var movies: List<Movie>? = mutableListOf()
    private var moviesFiltered: List<Movie>? = mutableListOf()
    private var page: Int = 0
    private val mPresenterDelegate = MainPresenter(this)
    private val mRecycleViewAdapter = MainListAdapter(mutableListOf(), this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)

        val mRecyclerView = findViewById<RecyclerView>(R.id.recycleview_list)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mRecycleViewAdapter
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val shouldDoPaging = movies?.count()?.rem(10)

                if ((shouldDoPaging == 0 && !filtering)
                    && linearLayoutManager!!.itemCount <= linearLayoutManager.findLastVisibleItemPosition() + 2) {
                    page += 1
                    mPresenterDelegate.retrieveMoviesList(page)
                }

                filtering = false
            }
        })

        showLoading()
        mPresenterDelegate.retrieveMoviesList(page)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val mSearch = menu?.findItem(R.id.action_search)
        val mSearchView = mSearch?.actionView as SearchView

        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                moviesFiltered = movies?.filter {
                        movie -> movie.title.contains(query.toString(), true)
                }

                filtering = true
                mRecycleViewAdapter.setMoviesFilteredList(moviesFiltered!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        @Suppress("DEPRECATION")
        MenuItemCompat.setOnActionExpandListener(mSearch, object : MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                mRecycleViewAdapter.setMoviesList(movies!!, true)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun setMoviesList(movies: List<Movie>?) {
        hideLoading()

        if (movies != null) {
            this.movies = movies
            mRecycleViewAdapter.setMoviesList(movies, false)
        } else {

        }
    }

    override fun clicked(movie: Movie) {
        mPresenterDelegate.goToDetailsActivity(this, movie)
    }

    fun showLoading() {
        val loading = findViewById<ProgressBar>(R.id.loading)
        loading.visibility = View.VISIBLE
    }

    fun hideLoading() {
        val loading = findViewById<ProgressBar>(R.id.loading)
        loading.visibility = View.GONE
    }
}
