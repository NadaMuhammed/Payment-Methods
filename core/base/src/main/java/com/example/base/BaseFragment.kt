package com.example.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater) -> VB
) : Fragment() {

    private var _binding: VB? = null
    open val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.initializeUI()
    }

    override fun onResume() {
        super.onResume()

        handleState()

        handleSideEffect()

        binding.registerListeners()
    }

    open fun VB.initializeUI() {}

    open fun VB.registerListeners() {}

    open fun handleState() {}

    open fun handleSideEffect() {}
}