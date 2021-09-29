package com.android.movieapp.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.android.movieapp.R
import com.android.movieapp.base.BaseFragment
import com.android.movieapp.base.DataState
import com.android.movieapp.databinding.DetailFragmentBinding
import com.android.movieapp.util.Constants
import com.android.movieapp.util.ext.setGone
import com.android.movieapp.util.ext.showToast
import com.android.movieapp.view.MainActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailFragmentBinding>(R.layout.detail_fragment) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DetailFragmentBinding
        get() = DetailFragmentBinding::inflate

    private val viewModel: DetailViewModel by viewModels()

    override fun init() {
        binding?.viewModel = viewModel
        setUI()
        getDetail()
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
                        .load(Constants.Server.backdropUrl.plus(MainActivity.selectedItem?.backdropPath))
                        .placeholder(circularProgress)
                        .into(it1)
                }
            }


        }
    }

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
            navigate(R.id.action_detailFragment_to_imdbFragment)
        }
    }

}