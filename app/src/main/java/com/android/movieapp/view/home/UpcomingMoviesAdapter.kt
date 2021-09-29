package com.android.movieapp.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.android.movieapp.R
import com.android.movieapp.model.MovieItemModel
import com.android.movieapp.util.Constants
import com.android.movieapp.util.DateUtils
import com.bumptech.glide.Glide

class UpcomingMoviesAdapter(
    val context: Context?,
    var list: List<MovieItemModel?>,
    val clickListener: (MovieItemModel?) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val VIEW_ITEM = 1
    private val VIEW_PROG = 0

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.ivItemUpcoming)
        val title: TextView = view.findViewById(R.id.tvItemUpcomingTitle)
        val desc: TextView = view.findViewById(R.id.tvItemUpcomingDesc)
        val date: TextView = view.findViewById(R.id.tvItemUpcomingDate)
    }

    class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


    override fun getItemViewType(position: Int): Int {
        return if (list[position] != null) VIEW_ITEM else VIEW_PROG
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh = if (viewType == VIEW_ITEM) {
            ItemViewHolder(
                LayoutInflater.from(context)
                    .inflate(R.layout.item_upcoming, parent, false)
            )
        } else {
            ProgressViewHolder(
                LayoutInflater.from(context)
                    .inflate(R.layout.item_progress, parent, false)
            )
        }
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val item = list.get(position)
            if (context != null) {

                val circularProgress = CircularProgressDrawable(context)
                circularProgress.strokeWidth = 5f
                circularProgress.centerRadius = 30f
                circularProgress.start()

                Glide.with(context)
                    .load(Constants.Server.logoUrl.plus(item?.poster_path))
                    .placeholder(circularProgress)
                    .into(holder.image)
            }
            holder.title.text = item?.title
            holder.desc.text = item?.overview
            holder.date.text = DateUtils.convertApiDateToAppDate(item?.release_date)

            holder.itemView.setOnClickListener {
                clickListener(item)
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}


