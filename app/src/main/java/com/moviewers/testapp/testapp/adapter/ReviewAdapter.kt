package com.moviewers.testapp.testapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moviewers.testapp.testapp.R
import com.moviewers.testapp.testapp.model.ResultReview

class ReviewAdapter(var reviews: List<ResultReview>, var context: Context) :
    RecyclerView.Adapter<ReviewAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_review, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        val reviewItem = reviews[i]

        myViewHolder.name.text = reviewItem.author
        myViewHolder.desc.text = reviewItem.content

    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var desc: TextView

        init {
            name = itemView.findViewById(R.id.tv_nama_reviewer)
            desc = itemView.findViewById(R.id.tv_review)
        }
    }
}