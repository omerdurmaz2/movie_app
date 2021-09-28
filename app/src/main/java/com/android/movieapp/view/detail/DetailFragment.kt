package com.android.movieapp.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.movieapp.R
import com.android.movieapp.base.BaseFragment
import com.android.movieapp.base.DataState
import com.android.movieapp.databinding.DetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailFragmentBinding>(R.layout.detail_fragment) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DetailFragmentBinding
        get() = DetailFragmentBinding::inflate

    private val viewModel: DetailViewModel by viewModels()

    override fun init() {
        binding.viewModel = viewModel
        binding.tvFragmentDetail.setOnClickListener {
            findNavController().popBackStack()
        }
        getDetail()
    }

    private fun getDetail() {
        viewModel.getDetail {
            when (it) {
                is DataState.Success<*> -> {
                    binding.tvFragmentDetail.text = viewModel.detail.toString()
                }
                is DataState.Error -> {

                }
            }
        }
    }

}