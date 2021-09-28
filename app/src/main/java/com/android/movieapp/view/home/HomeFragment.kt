package com.android.movieapp.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.android.movieapp.R
import com.android.movieapp.base.BaseFragment
import com.android.movieapp.base.DataState
import com.android.movieapp.databinding.HomeFragmentBinding
import com.android.movieapp.view.MainActivity
import com.android.movieapp.view.MainViewModel
import com.google.android.material.slider.Slider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(R.layout.home_fragment) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> HomeFragmentBinding
        get() = HomeFragmentBinding::inflate

    private val viewModel: HomeViewModel by viewModels()

    override fun init() {
        binding.viewModel = viewModel

        getPlayingMovies()
    }

    private fun getPlayingMovies() {
        viewModel.getPlayingMovies {
            when (it) {
                is DataState.Success<*> -> {
                    binding.vpFragmentHome.adapter =
                        context?.let { it1 ->
                            viewModel.playingMovies?.let { it2 ->
                                SliderAdapter(
                                    it1,
                                    it2
                                )
                            }
                        }
                }
                is DataState.Error -> {

                }
            }
        }
    }

}