package com.moviewers.testapp.testapp.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.moviewers.testapp.testapp.ListMovieActivity
import com.moviewers.testapp.testapp.R
import com.moviewers.testapp.testapp.model.Genre


class GenreAdapter(var genre: List<Genre>, var context: Context, var allData: Boolean) :
    RecyclerView.Adapter<GenreAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_genre, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        val genreItem = genre[i]
        myViewHolder.title.text = genreItem.name

        myViewHolder.card.setOnClickListener {
            val toMovie = Intent(context, ListMovieActivity::class.java)
            toMovie.putExtra("genreId", genreItem.id.toString())
            Log.d("kesini", "put "+genreItem.id.toString())
            context.startActivity(toMovie)
        }
    }

    override fun getItemCount(): Int {
        return if (allData) genre.size else 4
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var card: CardView

        init {
            title = itemView.findViewById(R.id.nama_genre)
            card = itemView.findViewById(R.id.cv_genre)
        }
    }
}