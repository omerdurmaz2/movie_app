package com.android.movieapp.view.imdb

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import com.android.movieapp.R
import com.android.movieapp.base.BaseFragment
import com.android.movieapp.databinding.ImdbFragmentBinding
import com.android.movieapp.util.Constants
import com.android.movieapp.util.ext.setGone
import com.android.movieapp.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImdbFragment : BaseFragment<ImdbFragmentBinding>(R.layout.imdb_fragment) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ImdbFragmentBinding
        get() = ImdbFragmentBinding::inflate

    private val viewModel: ImdbViewModel by viewModels()

    override fun init() {
        binding?.viewModel = viewModel
        setUI()
        listenWebView()
    }


    private fun setUI() {
        binding?.wvImdbFragment?.loadUrl(
            Constants.Server.imdbUrl.plus(MainActivity.selectedDetail?.imdbId)
        )
    }

    private fun listenWebView() {
        binding?.wvImdbFragment?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding?.llImdbLoading?.setGone()
            }
        }
    }


}