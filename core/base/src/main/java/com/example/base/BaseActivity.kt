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

        // Called once. Collect inside repeatOnLifecycle(STARTED) so collection
        // pauses in the background and resumes without adding duplicate collectors.
        handleState()

        handleSideEffect()
    }

    open fun VB.initializeUI() {}

    /** Start collecting state here, inside lifecycleScope + repeatOnLifecycle. Called once from onCreate. */
    open fun handleState() {}

    /** Start collecting side effects here, inside lifecycleScope + repeatOnLifecycle. Called once from onCreate. */
    open fun handleSideEffect() {}
}