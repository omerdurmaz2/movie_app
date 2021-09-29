package com.android.movieapp.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.viewpager.widget.PagerAdapter
import com.android.movieapp.R
import com.android.movieapp.model.BaseModel
import com.android.movieapp.model.MovieItemModel
import com.android.movieapp.util.Constants
import com.bumptech.glide.Glide

class SliderAdapter(
    private val context: Context,
    private val movies: BaseModel,
    private val clickListener: (MovieItemModel) -> Unit
) :
    PagerAdapter() {
    override fun getCount(): Int {
        return movies.results.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val view: ViewGroup =
            layoutInflater.inflate(R.layout.item_slider, container, false) as ViewGroup


        val circularProgress = CircularProgressDrawable(context)
        circularProgress.strokeWidth = 5f
        circularProgress.centerRadius = 30f
        circularProgress.start()

        movies.results[position].let {
            Glide.with(context)
                .load(Constants.Server.backdropUrl.plus(it.backdrop_path))
                .placeholder(circularProgress)
                .into(view.findViewById(R.id.ivItemSlider))
            view.findViewById<TextView>(R.id.tvItemSliderTitle).text = it.title
            view.findViewById<TextView>(R.id.tvItemSliderDesc).text = it.overview

        }

        view.setOnClickListener {
            clickListener(movies.results[position])
        }

        container.addView(view)
        return view
    }
}