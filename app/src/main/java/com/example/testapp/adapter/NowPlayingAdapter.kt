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
import com.example.testapp.model.NowPlaying
import com.example.testapp.model.Result

class NowPlayingAdapter(var nowPlaying: List<Result>, var context: Context) :
    RecyclerView.Adapter<NowPlayingAdapter.MyViewHolder>() {

    private val baseImage = "https://image.tmdb.org/t/p/w500"

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_now_playing, viewGroup, false)
        return MyViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        val nowPlayingItem = nowPlaying[i]
        myViewHolder.title.text = nowPlayingItem.title
        myViewHolder.release.text = DateFormatter.formatDate(nowPlayingItem.releaseDate.orEmpty())
        myViewHolder.rating.text = nowPlayingItem.voteAverage.toString()

        if (nowPlayingItem.posterPath != null) {
            val requestOptions = RequestOptions()
            Glide.with(context)
                .load(baseImage + nowPlayingItem.posterPath)
                .apply(requestOptions).into(myViewHolder.poster)
        }

        myViewHolder.card.setOnClickListener {
            val toDetail = Intent(context, DetailMovieActivity::class.java)
            toDetail.putExtra("idMovie", nowPlayingItem.id.toString())
            context.startActivity(toDetail)
        }
    }

    override fun getItemCount(): Int {
        return 5
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