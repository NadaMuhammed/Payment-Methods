package com.example.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater) -> VB
) : AppCompatActivity() {

    private var _binding: VB? = null
    open val binding get() = checkNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)

        binding.initializeUI()
    }

    override fun onResume() {
        super.onResume()

        handleState()

        handleSideEffect()
    }

    open fun VB.initializeUI() {}

    open fun handleState() {}

    open fun handleSideEffect() {}
}