package com.phoenixdevelopers.pixabay.ui.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.phoenixdevelopers.pixabay.R
import com.phoenixdevelopers.pixabay.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?
    ): View {

        _binding = FragmentSplashBinding
            .inflate(layoutInflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gotoNextScreen()

    }

    private fun gotoNextScreen() {

        lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {

                delay(2000)

                gotoHomeScreen()

            }
        }

    }

    private fun gotoHomeScreen() {

        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

    }
}