package com.example.testapp.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.testapp.DetailMovieActivity
import com.example.testapp.R
import com.example.testapp.helper.DateFormatter
import com.example.testapp.model.Result

class MoviesAdapter(var movieData: List<Result>, var context: Context) :
    RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {

    private val baseImage = "https://image.tmdb.org/t/p/w500"

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_now_playing, viewGroup, false)
        return MyViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        val movieItem = movieData[i]
        myViewHolder.title.text = movieItem.title
        myViewHolder.release.text = DateFormatter.formatDate(movieItem.releaseDate.orEmpty())
        myViewHolder.rating.text = movieItem.voteAverage.toString()

        if (movieItem.posterPath != null) {
            val requestOptions = RequestOptions()
            Glide.with(context)
                .load(baseImage + movieItem.posterPath)
                .apply(requestOptions).into(myViewHolder.poster)
        }

        myViewHolder.card.setOnClickListener {
            val toDetail = Intent(context, DetailMovieActivity::class.java)
            toDetail.putExtra("idMovie", movieItem.id.toString())
            context.startActivity(toDetail)
        }
    }

    override fun getItemCount(): Int {
        return movieData.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var card: CardView
        var release: TextView
        var rating: TextView
        var poster: ImageView

        init {
            title = itemView.findViewById(R.id.tv_title_name)
            card = itemView.findViewById(R.id.cv_movie)
            release = itemView.findViewById(R.id.tv_release)
            rating = itemView.findViewById(R.id.tv_rating)
            poster = itemView.findViewById(R.id.image_movie)
        }
    }
}