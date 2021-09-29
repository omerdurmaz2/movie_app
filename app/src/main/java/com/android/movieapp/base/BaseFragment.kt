package com.android.movieapp.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.android.movieapp.util.ext.popBackStackAllInstances

abstract class BaseFragment<VB : ViewBinding>(
    private val layout: Int,
) : Fragment() {

    private var _binding: VB? = null
    val binding: VB? get() = _binding
    var isNavigated = false
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.e("sss", "isNavigated $isNavigated")

        if (!isNavigated)
            _binding = bindingInflater(layoutInflater, container, false)
        return _binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initClickListeners()
        initFocusListeners()
    }

    /**
     * initialize the fragment
     */
    abstract fun init()
    open fun initClickListeners() {}
    open fun initFocusListeners() {}


    fun navigateWithAction(action: NavDirections) {
        isNavigated = true
        findNavController().navigate(action)
    }

    fun navigate(resId: Int) {
        isNavigated = true
        findNavController().navigate(resId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (!isNavigated)
            requireActivity().onBackPressedDispatcher.addCallback(this) {
                val navController = findNavController()
                if (navController.currentBackStackEntry?.destination?.id != null) {
                    findNavController().popBackStackAllInstances(
                        navController.currentBackStackEntry?.destination?.id!!,
                        true
                    )
                } else navController.popBackStack()
            }

    }


}