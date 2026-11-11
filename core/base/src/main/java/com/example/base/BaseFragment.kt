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

        // Called once per view. Listeners like addTextChangedListener would stack up
        // if they were added again on every resume.
        binding.registerListeners()

        // Called once per view. Collect inside viewLifecycleOwner.repeatOnLifecycle(STARTED)
        // so collection pauses in the background and stops when the view is destroyed.
        handleState()

        handleSideEffect()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // The fragment can outlive its view (e.g. in the back stack); don't keep the old views.
        _binding = null
    }

    open fun VB.initializeUI() {}

    open fun VB.registerListeners() {}

    /**
     * Start collecting state here, using viewLifecycleOwner:
     * viewLifecycleOwner.lifecycleScope.launch { viewLifecycleOwner.repeatOnLifecycle(STARTED) { ... } }
     * Called once per view from onViewCreated.
     */
    open fun handleState() {}

    /** Start collecting side effects here, the same way as [handleState]. Called once per view from onViewCreated. */
    open fun handleSideEffect() {}
}
