package com.bl4ckcode.traktapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bl4ckcode.traktapp.R
import com.bl4ckcode.traktapp.model.Movie
import kotlinx.android.synthetic.main.adapter_list_main.view.*

interface OnAdapterListItemListener {
    fun clicked(movie: Movie)
}

class MainListAdapter(private var movies: MutableList<Movie>,
                      private val context: Context,
                      private val adapterClickListener: OnAdapterListItemListener): RecyclerView.Adapter<MainListAdapter.MainListAdapterViewHolder>() {

    class MainListAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        //val imViewMovieIcon = itemView.imView_movie_icon
        val txtViewMovieTitle = itemView.txtView_titulo_filme
        val txtViewMovieYear = itemView.txtView_ano_filme
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListAdapterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_list_main, parent, false)
        return MainListAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.count()
    }

    override fun onBindViewHolder(holder: MainListAdapterViewHolder, position: Int) {
        //holder.imViewMovieIcon ??
        holder.txtViewMovieTitle.text = movies[holder.adapterPosition].title
        holder.txtViewMovieYear.text = movies[holder.adapterPosition].year.toString()
        holder.itemView.setOnClickListener { adapterClickListener.clicked(movies[holder.adapterPosition]) }
    }

    fun setMoviesList(movies: List<Movie>, clear: Boolean) {
        var startIndex = this.movies.count()

        if (clear) {
            startIndex = 0
            this.movies.clear()
        }

        this.movies.addAll(movies)
        notifyItemRangeChanged(startIndex, this.movies.count())
    }

    fun setMoviesFilteredList(movies: List<Movie>) {
        this.movies = movies.toMutableList()
        notifyDataSetChanged()
    }
}