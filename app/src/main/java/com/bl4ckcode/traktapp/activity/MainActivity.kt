package com.bl4ckcode.traktapp.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bl4ckcode.traktapp.R
import com.bl4ckcode.traktapp.adapter.MainListAdapter
import com.bl4ckcode.traktapp.adapter.OnAdapterListItemListener
import com.bl4ckcode.traktapp.model.Movie
import com.bl4ckcode.traktapp.presenter.MainPresenter
import com.bl4ckcode.traktapp.presenter.MainPresenterDelegate

class MainActivity : AppCompatActivity(), MainPresenterDelegate, OnAdapterListItemListener {

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

                if (linearLayoutManager!!.itemCount <= linearLayoutManager.findLastVisibleItemPosition() + 2) {
                    page += 1
                    mPresenterDelegate.retrieveMoviesList(page)
                }
            }
        })

        mPresenterDelegate.retrieveMoviesList(page)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val mSearch = menu?.findItem(R.id.action_search)
        val mSearchView = mSearch?.actionView as SearchView

        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun setMoviesList(movies: List<Movie>?) {
        if (movies != null) {
            mRecycleViewAdapter.setMoviesList(movies)
        } else {

        }
    }

    override fun clicked(movie: Movie) {
        mPresenterDelegate.goToDetailsActivity(this, movie)
    }
}
