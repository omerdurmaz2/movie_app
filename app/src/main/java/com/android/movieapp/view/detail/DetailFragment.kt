package com.android.movieapp.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.android.movieapp.R
import com.android.movieapp.base.BaseFragment
import com.android.movieapp.base.DataState
import com.android.movieapp.databinding.DetailFragmentBinding
import com.android.movieapp.model.MovieDetailModel
import com.android.movieapp.model.MovieItemModel
import com.android.movieapp.util.Constants
import com.android.movieapp.util.ext.setGone
import com.android.movieapp.util.ext.showToast
import com.android.movieapp.view.MainActivity
import com.bumptech.glide.Glide
import com.google.firebase.perf.metrics.AddTrace
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailFragmentBinding>(R.layout.detail_fragment) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DetailFragmentBinding
        get() = DetailFragmentBinding::inflate

    private val viewModel: DetailViewModel by viewModels()

    override fun init() {
        binding?.viewModel = viewModel

        arguments?.getString("movie").let {
            if (it != null) {
                viewModel.selectedItem =
                    viewModel.gson.fromJson(
                        it,
                        MovieItemModel::class.java
                    )
                setUI()
                getDetail()
            } else activity?.onBackPressed()
        }
    }

    private fun setUI() {
        val circularProgress = context?.let { CircularProgressDrawable(it) }
        circularProgress?.strokeWidth = 5f
        circularProgress?.centerRadius = 30f
        circularProgress?.start()
        context?.let {
            context?.let {
                binding?.ivDetailFragmentBanner?.let { it1 ->
                    Glide.with(it)
                        .load(Constants.Server.backdropUrl.plus(viewModel.selectedItem?.backdropPath))
                        .placeholder(circularProgress)
                        .into(it1)
                }
            }


        }
    }

    @AddTrace(name = "get_movie_detail_trace", enabled = true)
    private fun getDetail() {
        viewModel.getDetail {
            when (it) {
                is DataState.Success<*> -> {
                    binding?.llDetailFragmentLoading?.setGone()
                }
                is DataState.Error -> {
                    showToast(getString(R.string.technical_error_message))
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }
            }
        }
    }

    override fun initClickListeners() {
        super.initClickListeners()
        binding?.ivDetailImdb?.setOnClickListener {
            navigateToImdb()
        }
    }

    private fun navigateToImdb() {
        val bundle = Bundle()
        bundle.putString("detail", viewModel.gson.toJson(viewModel.detail.get()))
        navigate(R.id.action_detailFragment_to_imdbFragment, bundle)
    }

}