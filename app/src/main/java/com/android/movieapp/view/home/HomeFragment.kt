package com.android.movieapp.view.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.movieapp.R
import com.android.movieapp.base.BaseFragment
import com.android.movieapp.base.DataState
import com.android.movieapp.databinding.HomeFragmentBinding
import com.android.movieapp.util.ext.setGone
import com.android.movieapp.util.ext.setVisible
import com.android.movieapp.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(R.layout.home_fragment) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> HomeFragmentBinding
        get() = HomeFragmentBinding::inflate

    private val viewModel: HomeViewModel by viewModels()

    override fun init() {
        binding?.viewModel = viewModel

        if (!isNavigated) {
            initRecyclerView()
            initSwipeRefresh()
            binding?.tlHomeFragment?.setupWithViewPager(binding?.vpFragmentHome, true)
            getPlayingMovies()
        }
    }

    private fun initSwipeRefresh() {
        binding?.srHomeFragment?.setOnRefreshListener {
            viewModel.upcomingMovies.clear()
            viewModel.page = 1
            getPlayingMovies()
        }
    }

    private fun getPlayingMovies() {
        viewModel.getPlayingMovies {
            when (it) {
                is DataState.Success<*> -> {
                    binding?.vpFragmentHome?.adapter =
                        context?.let { it1 ->
                            viewModel.playingMovies?.let { it2 ->
                                SliderAdapter(
                                    it1,
                                    it2
                                ) {
                                    MainActivity.selectedItem = it
                                    navigate(
                                        R.id.action_homeFragment_to_detailFragment,
                                    )
                                }
                            }
                        }
                    getUpcomingMovies()
                }
                is DataState.Error -> {
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }
            }
        }
    }

    private fun getUpcomingMovies() {
        viewModel.getUpcomingMovies {
            when (it) {
                is DataState.Success<*> -> {
                    notifyAdapter()
                }
                is DataState.Error -> {
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }
            }
        }
    }

    private fun initRecyclerView() {
        viewModel.listAdapter = UpcomingMoviesAdapter(context, viewModel.upcomingMovies) { i ->
            MainActivity.selectedItem = i
            navigate(
                R.id.action_homeFragment_to_detailFragment,
            )
        }
        binding?.rvHomeFragment?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModel.listAdapter
        }

        binding?.nsHomeFragment?.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                if (viewModel.baseUpcomingMovies?.results?.size != 0 && !viewModel.isLoading) {
                    viewModel.isLoading = true
                    viewModel.page++
                    showLoadMoreIndicator()
                    getUpcomingMovies()
                }
            }
        })
    }

    private fun showLoadMoreIndicator() {
        viewModel.upcomingMovies.add(null)
        viewModel.listAdapter.notifyItemInserted(viewModel.upcomingMovies.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun notifyAdapter() {
        if (binding?.srHomeFragment?.visibility == View.GONE) {
            binding?.srHomeFragment?.setVisible()
            binding?.llHomeFragmentLoading?.setGone()
            viewModel.listAdapter.notifyDataSetChanged()
        }

        if (binding?.srHomeFragment?.isRefreshing == true) {
            viewModel.listAdapter.notifyDataSetChanged()
            binding?.srHomeFragment?.isRefreshing = false
        } else {
            viewModel.listAdapter.notifyItemInserted(
                viewModel.upcomingMovies.size - (viewModel.baseUpcomingMovies?.results?.size ?: 0)
            )
            viewModel.isLoading = false
        }

    }

}